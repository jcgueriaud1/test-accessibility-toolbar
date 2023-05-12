package org.jchristophe.accessibility.components.toolbar;

import com.vaadin.base.devserver.IdeIntegration;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.HasAriaLabel;
import com.vaadin.flow.component.HasLabel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.startup.ApplicationConfiguration;
import org.jchristophe.accessibility.utility.LabelParsingSummary;

/**
 * @author jcgueriaud
 */
public class AccessibilityToolbarLabelSummary extends VerticalLayout {

    private final Checkbox highlightErrors = new Checkbox("Highlight Errors");
    private final Button focusNextInvalidComponentButton = new Button("Next");
    private final Button focusPreviousInvalidComponentButton = new Button("Previous");
    private final Button openInIdeButton = new Button("Component");
    private final Button openAttachedInIdeButton = new Button("On Attach");
    private final Button showAriaLabelButton = new Button("Display Aria Label");

    // show aria label as label
    private final Span currentElementComponentName = new Span();

    private int currentFocusComponentIndex = -1;

    private LabelParsingSummary labelParsingSummary;

    private IdeIntegration ideIntegration;

    private boolean showAriaLabel = false;

    public AccessibilityToolbarLabelSummary(LabelParsingSummary labelParsingSummary) {
        setPadding(false);
        this.labelParsingSummary = labelParsingSummary;
        highlightErrors.addValueChangeListener(e -> {
            for (Component invalidField : labelParsingSummary.getInvalidFields()) {
                invalidField.getElement().getClassList().set("accessibility-toolbar__invalid-label", e.getValue());
            }
        });
        focusNextInvalidComponentButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        focusPreviousInvalidComponentButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        openInIdeButton.setEnabled(false);
        openInIdeButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        openAttachedInIdeButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        openAttachedInIdeButton.setEnabled(false);
        if (labelParsingSummary.getInvalidFields().isEmpty()) {
            focusPreviousInvalidComponentButton.setEnabled(false);
            focusNextInvalidComponentButton.setEnabled(false);
            openInIdeButton.setEnabled(false);
            openAttachedInIdeButton.setEnabled(false);
            add("No error");
            getElement().getClassList().set("accessibility-toolbar__valid",true);
            getElement().getClassList().set("accessibility-toolbar__invalid",false);
        } else {
            getElement().getClassList().set("accessibility-toolbar__valid",false);
            getElement().getClassList().set("accessibility-toolbar__invalid",true);
            add(labelParsingSummary.getInvalidCount() +" errors");

            add(highlightErrors,
                    new HorizontalLayout(currentElementComponentName),
                    new HorizontalLayout(focusPreviousInvalidComponentButton, focusNextInvalidComponentButton),
                    new HorizontalLayout(new Span("Open in IDE")),
                    new HorizontalLayout(openInIdeButton, openAttachedInIdeButton));
            focusNext();
        }
        focusPreviousInvalidComponentButton.addClickListener(e -> {
            focusPrevious();
        });
        focusNextInvalidComponentButton.addClickListener(e -> {
            focusNext();
        });

        openInIdeButton.addClickListener(e -> {
            Component component = labelParsingSummary.getInvalidFields().get(currentFocusComponentIndex);
            getIdeIntegration().showComponentCreateInIde(component);
        });
        openAttachedInIdeButton.addClickListener(e -> {
            Component component = labelParsingSummary.getInvalidFields().get(currentFocusComponentIndex);
            getIdeIntegration().showComponentAttachInIde(component);
        });
        add(new Span("Label found: " + labelParsingSummary.getLabelFoundCount()),
                new Span("Aria Label found: " + labelParsingSummary.getAriaLabelFoundCount()));

        if (labelParsingSummary.getAriaLabelFoundCount() > 0) {
            add(showAriaLabelButton);
            showAriaLabelButton.addClickListener(e -> {
                showAriaLabel = !showAriaLabel;
                if (showAriaLabel) {
                    showAriaLabelButton.setText("Hide Aria Label");
                    for (Component ariaLabelOnlyField : labelParsingSummary.getAriaLabelOnlyFields()) {
                        if (ariaLabelOnlyField instanceof HasLabel && ariaLabelOnlyField instanceof HasAriaLabel) {
                            ((HasAriaLabel) ariaLabelOnlyField).getAriaLabel().ifPresent(arialabel -> {
                                ((HasLabel) ariaLabelOnlyField).setLabel(arialabel);
                            });
                        }
                    }
                } else {
                    showAriaLabelButton.setText("Hide Show Label");
                    for (Component ariaLabelOnlyField : labelParsingSummary.getAriaLabelOnlyFields()) {
                        if (ariaLabelOnlyField instanceof HasLabel) {
                            ((HasLabel) ariaLabelOnlyField).setLabel(null);
                        }
                    }
                }
            });
        }
    }

    private void focusPrevious() {
        currentFocusComponentIndex--;
        focusIndexedComponent();
    }

    private void focusNext() {
        currentFocusComponentIndex++;
        focusIndexedComponent();
    }

    private void focusIndexedComponent() {
        if (currentFocusComponentIndex < 0) {
            currentFocusComponentIndex = labelParsingSummary.getInvalidCount() - 1;
        }
        if (currentFocusComponentIndex >= labelParsingSummary.getInvalidCount()) {
            currentFocusComponentIndex = 0;
        }
        Component component = labelParsingSummary.getInvalidFields().get(currentFocusComponentIndex);
        if (component instanceof Focusable<?>) {
            ((Focusable<?>) component).focus();
        } else {
            Notification.show("The component is not focusable " + component.getClass().getSimpleName());
        }

        openInIdeButton.setEnabled(true);
        openAttachedInIdeButton.setEnabled(true);
        currentElementComponentName.setText("Current Component: " + component.getClass().getSimpleName());
    }

    private IdeIntegration getIdeIntegration() {
        if (ideIntegration == null) {
            ideIntegration = new IdeIntegration(ApplicationConfiguration.get(VaadinService.getCurrent().getContext()));
        }
        return ideIntegration;
    }
}
