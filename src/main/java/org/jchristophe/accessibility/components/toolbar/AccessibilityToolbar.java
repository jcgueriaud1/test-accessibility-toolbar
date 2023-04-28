package org.jchristophe.accessibility.components.toolbar;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.data.binder.Binder;

/**
 * @author jcgueriaud
 */
public class AccessibilityToolbar extends Composite<VerticalLayout> {

    private final RadioButtonGroup<ThemeVariantConfiguration> switchThemeVariantButton;
    private Binder<AccessibilityConfiguration> binder;

    public AccessibilityToolbar() {
        switchThemeVariantButton = new RadioButtonGroup<>();
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


        /**{
            line - height:1.5 !important;
            letter - spacing:0.12em !important;
            word - spacing:0.16em !important;
        }
        p {
            margin - bottom:2em !important;
        }*/

        configureBinder();
        getContent().add(switchThemeVariantButton);
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
