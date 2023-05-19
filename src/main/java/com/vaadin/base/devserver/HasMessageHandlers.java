package com.vaadin.base.devserver;

import com.vaadin.base.devserver.themeeditor.messages.BaseResponse;
import com.vaadin.base.devserver.themeeditor.messages.ErrorResponse;
import com.vaadin.base.devserver.themeeditor.utils.MessageHandler;
import com.vaadin.base.devserver.themeeditor.utils.ThemeEditorException;
import com.vaadin.base.devserver.themeeditor.utils.ThemeEditorHistory;
import elemental.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Set;

public interface HasMessageHandlers {

    /**
     * Checks if given command can be handled by ThemeEditor.
     *
     * @param command
     *            command to be verified if supported
     * @param data
     *            data object to be verified if is of proper structure
     * @return true if it can be handled, false otherwise
     */
    default boolean canHandle(String command, JsonObject data) {
        return command != null && data != null /*&& data.hasKey("requestId")*/ //todo jcg
                && getHandler(command).isPresent();
    }


     default Optional<MessageHandler> getHandler(String command) {
        return getHandlers().stream().filter(h -> h.getCommandName().equals(command))
                .findFirst();
    }
    /**
     * Handles debug message command and performs given action.
     *
     * @param command
     *            Command name
     * @param data
     *            Command data
     * @return response in form of JsonObject
     */
    default BaseResponse handleDebugMessageData(String command,
                                               JsonObject data) {
        assert canHandle(command, data);
        String requestId;
        if (data.hasKey("requestId")) {
            requestId = data.getString("requestId");
        } else {
            requestId = "todoJCG"; //todo jcg
        }
        Integer uiId = (int) data.getNumber("uiId");
        ThemeEditorHistory history = ThemeEditorHistory.forUi(uiId);
        try {
            MessageHandler.ExecuteAndUndo executeAndUndo = getHandler(command)
                    .get().handle(data);
            executeAndUndo.undoCommand()
                    .ifPresent(undo -> history.put(requestId, executeAndUndo));
            BaseResponse response = executeAndUndo.executeCommand().execute();
            response.setRequestId(requestId);
            return response;
        } catch (ThemeEditorException ex) {
            getLogger().error(ex.getMessage(), ex);
            return new ErrorResponse(requestId, ex.getMessage());
        }
    }

    Set<MessageHandler> getHandlers();


    static Logger getLogger() {
        return LoggerFactory
                .getLogger(HasMessageHandlers.class.getName());
    }
}
