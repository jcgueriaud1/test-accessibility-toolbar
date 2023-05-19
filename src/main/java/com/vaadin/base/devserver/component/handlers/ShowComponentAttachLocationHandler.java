package com.vaadin.base.devserver.component.handlers;

import com.vaadin.base.devserver.IdeIntegration;
import com.vaadin.base.devserver.component.ComponentCommand;
import com.vaadin.base.devserver.themeeditor.messages.BaseRequest;
import com.vaadin.base.devserver.themeeditor.messages.BaseResponse;
import com.vaadin.base.devserver.themeeditor.utils.MessageHandler;
import com.vaadin.base.devserver.themeeditor.utils.ThemeEditorException;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.internal.JsonUtils;
import com.vaadin.flow.server.VaadinSession;
import elemental.json.JsonObject;

import java.util.Optional;

import static com.vaadin.base.devserver.HasMessageHandlers.getLogger;

public class ShowComponentAttachLocationHandler implements MessageHandler {

    private final IdeIntegration ideIntegration;

    public ShowComponentAttachLocationHandler(IdeIntegration ideIntegration) {
        this.ideIntegration = ideIntegration;
    }

    @Override
    public ExecuteAndUndo handle(JsonObject data) {
        BaseRequest request = JsonUtils.readToObject(data, BaseRequest.class);

        if (!request.isInstanceRequest()) {
            throw new ThemeEditorException(
                    "Cannot load metadata - uiId or nodeId are missing.");
        }


        return new ExecuteAndUndo(
                () -> {
                    int nodeId = request.getNodeId();
                    int uiId = request.getUiId();
                    VaadinSession session = VaadinSession.getCurrent();
                    session.access(() -> {
                        Element element = session.findElement(uiId, nodeId);
                        Optional<Component> c = element.getComponent();
                        if (c.isPresent()) {
                            ideIntegration.showComponentAttachInIde(c.get());
                        } else {
                            getLogger().error(
                                    "Only component locations are tracked. The given node id refers to an element and not a component");
                        }
                    });
                    return BaseResponse.ok();
                },
                Optional.empty());
    }

    @Override
    public String getCommandName() {
        return ComponentCommand.SHOW_COMPONENT_ATTACH_LOCATION;
    }
}
