package com.vaadin.base.devserver.component;

import com.vaadin.base.devserver.HasMessageHandlers;
import com.vaadin.base.devserver.IdeIntegration;
import com.vaadin.base.devserver.component.handlers.ShowComponentAttachLocationHandler;
import com.vaadin.base.devserver.component.handlers.ShowComponentCreateLocationHandler;
import com.vaadin.base.devserver.themeeditor.JavaSourceModifier;
import com.vaadin.base.devserver.themeeditor.ThemeModifier;
import com.vaadin.base.devserver.themeeditor.utils.MessageHandler;
import com.vaadin.flow.server.VaadinContext;
import com.vaadin.flow.server.startup.ApplicationConfiguration;

import java.util.HashSet;
import java.util.Set;

/**
 * Handler for ThemeEditor debug window communication messages. Responsible for
 * preparing data for {@link ThemeModifier} and {@link JavaSourceModifier}.
 */
public class ComponentMessageHandler
        implements HasMessageHandlers {

    private final Set<MessageHandler> handlers = new HashSet<>();
    private final IdeIntegration ideIntegration;

    public ComponentMessageHandler(VaadinContext context) {
        this.ideIntegration = new IdeIntegration(
                ApplicationConfiguration.get(context));
        this.handlers.add(new ShowComponentCreateLocationHandler(ideIntegration));
        this.handlers.add(new ShowComponentAttachLocationHandler(ideIntegration));
    }

    @Override
    public Set<MessageHandler> getHandlers() {
        return handlers;
    }
}
