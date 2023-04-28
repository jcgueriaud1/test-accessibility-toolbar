package org.jchristophe.accessibility.views.testcomponents;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import org.jchristophe.accessibility.components.toolbar.AccessibilityConfiguration;
import org.jchristophe.accessibility.components.toolbar.AccessibilityToolbar;
import org.jchristophe.accessibility.components.toolbar.ThemeVariantConfiguration;
import org.jchristophe.accessibility.views.MainLayout;

@PageTitle("Test Components")
@Route(value = "test-component", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class TestComponentsView extends VerticalLayout {

    public TestComponentsView() {
        setSpacing(false);

        AccessibilityToolbar accessibilityToolbar = new AccessibilityToolbar();
        AccessibilityConfiguration accessibilityConfiguration = new AccessibilityConfiguration();
        accessibilityConfiguration.setThemeVariantConfiguration(ThemeVariantConfiguration.OS_PREFERRED);
        accessibilityToolbar.setAccessibilityConfiguration(accessibilityConfiguration);
        add(accessibilityToolbar);
    }

}
