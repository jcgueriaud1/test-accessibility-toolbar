package org.jchristophe.accessibility.components.toolbar;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.binder.Binder;
import org.jchristophe.accessibility.utility.ComponentParserUtil;
import org.jchristophe.accessibility.utility.LabelParsingSummary;

import static org.jchristophe.accessibility.utility.ComponentParserUtil.applyFunctionOnUI;

/**
 * @author jcgueriaud
 */
public class AccessibilityToolbar extends VerticalLayout {

    private Accordion accordion = new Accordion();

    private final Select<ThemeVariantConfiguration> switchThemeVariantButton;
    private final Button checkLabelsButton;
    private final Div checkLabelsResultContainer;

    private Binder<AccessibilityConfiguration> binder;

    public AccessibilityToolbar() {
        setPadding(false);
        addClassName("accessibility-toolbar");
        switchThemeVariantButton = new Select<>();
        switchThemeVariantButton.setLabel("Switch");
        switchThemeVariantButton.setItems(ThemeVariantConfiguration.values());
        switchThemeVariantButton.addValueChangeListener(event -> {
            switch (event.getValue()) {
                case LIGHT -> {
                    UI.getCurrent().getElement().removeAttribute("theme");
                    break;
                }
                case DARK -> {
                    UI.getCurrent().getElement().setAttribute("theme", "dark");
                }
                case OS_PREFERRED -> {
                    // set the theme immediately (so it won't blink) and also on the server side (to synchronize it properly)
                    UI.getCurrent().getPage().executeJs("if (window.matchMedia('(prefers-color-scheme: dark)').matches) " +
                                    "{document.body.setAttribute(\"theme\", \"dark\"); return true;} " +
                                    "else " +
                                    " {document.body.removeAttribute(\"theme\"); return false;}")
                            .then(Boolean.class, darkMode -> {
                                if (darkMode) {
                                    UI.getCurrent().getElement().setAttribute("theme", "dark");
                                } else {
                                    UI.getCurrent().getElement().removeAttribute("theme");
                                }
                            });
                }
            }
        });

        RadioButtonGroup<Boolean> disableComponentChoice = new RadioButtonGroup<>("Disable elements");
        disableComponentChoice.setItems(Boolean.TRUE, Boolean.FALSE);
        disableComponentChoice.setItemLabelGenerator(disableBoolean -> disableBoolean?"Enable": "Disable");
        disableComponentChoice.addValueChangeListener(e -> {
            applyFunctionOnUI(HasValueAndElement.class, component -> component.setEnabled(e.getValue()));
            applyFunctionOnUI(Button.class, component -> component.setEnabled(e.getValue()));
        });

        RadioButtonGroup<Boolean> invalidComponentChoice = new RadioButtonGroup<>("Invalidate elements");
        invalidComponentChoice.setItems(Boolean.FALSE, Boolean.TRUE);
        invalidComponentChoice.setItemLabelGenerator(disableBoolean -> disableBoolean?"Invalid": "Valid");
        invalidComponentChoice.addValueChangeListener(e -> {
            applyFunctionOnUI(HasValidation.class, component -> component.setInvalid(e.getValue()));
        });
        RadioButtonGroup<Boolean> readonlyComponentChoice = new RadioButtonGroup<>("Readonly elements");
        readonlyComponentChoice.setItems(Boolean.FALSE, Boolean.TRUE);
        readonlyComponentChoice.setItemLabelGenerator(disableBoolean -> disableBoolean?"Readonly": "Writable");
        readonlyComponentChoice.addValueChangeListener(e -> {
            applyFunctionOnUI(HasValueAndElement.class, component -> component.setReadOnly(e.getValue()));
        });
        checkLabelsResultContainer = new Div();
        checkLabelsResultContainer.setWidthFull();
        checkLabelsButton = new Button("Check labels", e -> {
            checkLabelsResultContainer.removeAll();
            LabelParsingSummary labelParsingSummary = ComponentParserUtil.validateLabels();
            checkLabelsResultContainer.add(new AccessibilityToolbarLabelSummary(labelParsingSummary));

        });
        checkLabelsButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        /**{
            line - height:1.5 !important;
            letter - spacing:0.12em !important;
            word - spacing:0.16em !important;
        }
        p {
            margin - bottom:2em !important;
        }*/

        VerticalLayout contentLabelCheck = new VerticalLayout(checkLabelsButton, checkLabelsResultContainer);
        contentLabelCheck.setPadding(false);
        accordion.add("Labels", contentLabelCheck);
        configureBinder();
        VerticalLayout contentTheme = new VerticalLayout(switchThemeVariantButton,
                disableComponentChoice, invalidComponentChoice, readonlyComponentChoice);
        contentTheme.setPadding(false);
        accordion.add("Theme",
                contentTheme);
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
        addAndExpand(accordion);
    }

    private void configureBinder() {
        binder = new Binder<>();
        binder.forField(switchThemeVariantButton)
                .bind(AccessibilityConfiguration::getThemeVariantConfiguration, AccessibilityConfiguration::setThemeVariantConfiguration);
    }

    public void setAccessibilityConfiguration(AccessibilityConfiguration accessibilityConfiguration) {
        binder.setBean(accessibilityConfiguration);
    }

}
