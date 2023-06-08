package org.jchristophe.accessibility.views.testcomponents;

import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.jchristophe.accessibility.views.MainLayout;

@PageTitle("Invalid Simple Page")
@Route(value = "invalid-simple-page", layout = MainLayout.class)
public class SimpleInvalidDemoView extends Main {

    public SimpleInvalidDemoView() {
        TextField textField = new TextField();
        add(textField);
    }

}
