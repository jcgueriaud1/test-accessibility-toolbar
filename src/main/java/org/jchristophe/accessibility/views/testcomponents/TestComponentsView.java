package org.jchristophe.accessibility.views.testcomponents;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.jchristophe.accessibility.components.toolbar.AccessibilityNativeDialog;
import org.jchristophe.accessibility.views.MainLayout;

@PageTitle("Test Components")
@Route(value = "test-component", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class TestComponentsView extends VerticalLayout {

    private AccessibilityNativeDialog accessibilityNativeDialog = new AccessibilityNativeDialog();
    private Dialog dialog = new Dialog();

    public TestComponentsView() {
        setSpacing(false);

        add(accessibilityNativeDialog);
        dialog.add(new Button("Close dialog", e -> dialog.close()));
        add(new Button("Open dialog", e -> dialog.open()));
    }

}
