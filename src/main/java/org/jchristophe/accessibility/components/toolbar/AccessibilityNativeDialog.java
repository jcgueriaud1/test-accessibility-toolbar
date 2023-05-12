package org.jchristophe.accessibility.components.toolbar;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;

/**
 * @author jcgueriaud
 */
@Tag("dialog")
public class AccessibilityNativeDialog extends Component implements HasComponents {

    private AccessibilityToolbar accessibilityToolbar = new AccessibilityToolbar();

    private Button minimizeButton;

    private boolean minimized = false;
    public AccessibilityNativeDialog() {
        addClassName("accessibility-dialog");
        getElement().executeJs("this.show()");
        AccessibilityConfiguration accessibilityConfiguration = new AccessibilityConfiguration();
        accessibilityConfiguration.setThemeVariantConfiguration(ThemeVariantConfiguration.LIGHT);
        accessibilityToolbar.setAccessibilityConfiguration(accessibilityConfiguration);
        add(accessibilityToolbar);
        minimizeButton = new Button("Open", VaadinIcon.ACCESSIBILITY.create(), e -> toggleMinimize());
        minimizeButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
        add(minimizeButton);
        toggleMinimize();
    }

    private void toggleMinimize() {
        minimized = !minimized;
        if (minimized) {
            minimizeButton.setText("Open");
        } else {
            minimizeButton.setText("Minimize");
        }
        getElement().getClassList().set("minimized", minimized);
    }

    public void open() {
        getElement().executeJs("this.show()");
    }

    public void close() {
        getElement().executeJs("this.close()");
    }
}
