package org.jchristophe.accessibility.views.testcomponents;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasEnabled;
import com.vaadin.flow.component.HasLabel;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.richtexteditor.RichTextEditor;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.SerializationUtils;
import org.jchristophe.accessibility.components.toolbar.AccessibilityNativeDialog;
import org.jchristophe.accessibility.views.MainLayout;

import java.time.LocalDate;
import java.util.Arrays;

@PageTitle("High Contrast")
@Route(value = "high-contrast", layout = MainLayout.class)
public class ComponentDemoView extends Main {

    private AccessibilityNativeDialog accessibilityNativeDialog = new AccessibilityNativeDialog();

    public ComponentDemoView() {
        add(accessibilityNativeDialog);

        TextField textField1 = new TextField();
        textField1.setLabel("Test label 1");
        add(textField1);
        TextField textField2 = new TextField();
        textField2.setAriaLabel("Test");
        add(textField2);

        TextField textField3 = new TextField();
        add(textField3);
        Div container = new Div();
        container.setClassName("contrast-border-samples");

        var comboBox = new ComboBox<String>();
        comboBox.setItems(Arrays.asList("Turku", "Berlin", "San Jose"));
        comboBox.setValue("Turku");
        var mscb = new MultiSelectComboBox<String>("Assign reviewers");
        mscb.setItems(Arrays.asList("Diego Cardoso", "Rolf Smeds", "Sascha Ißbrücker", "Sergey Vinogradov", "Serhii Kulykov", "Tomi Virkki", "Uğur Sağlam", "Yuriy Yevstihnyeyev"));
        mscb.setValue("Rolf Smeds");
        var radioButtonGroup = new RadioButtonGroup<String>("Travel class");
        radioButtonGroup.setItems("Economy", "Business", "First Class");
        radioButtonGroup.setValue("Business");

        addSampleLine(container, "Button", new Button("New user"));
        addSampleLine(container, "Combo Box", comboBox);
        addSampleLine(container, "Checkbox", new Checkbox("I accept the terms and conditions"));
        addSampleLine(container, "Date Picker", new DatePicker("Start date", LocalDate.now()));
        addSampleLine(container, "Multi Select Combo Box", mscb);
        addSampleLine(container, "Password", new PasswordField("Password", "xxxx", e -> {}));
        addSampleLine(container, "Progress Bar", new ProgressBar(0, 100, 75));
        addSampleLine(container, "Radio Button", radioButtonGroup);
        var rte = new RichTextEditor();
        rte.setValue("Hello, world!");
        rte.setMinWidth("300px");
        addSampleLine(container, "Rich Text Editor", rte);
        addSampleLine(container, "Tabs", new Tabs(new Tab("Details"), new Tab("Payment"), new Tab("Shipping")));
        addSampleLine(container, "Text Area", new TextArea("Details", "Some text", ""));
        addSampleLine(container, "Text Field", new TextField("Name", "Aria Bailey", ""));
        var dialog = new Dialog();
        var userName = new TextField("Name", "Aria Bailey", "");
        userName.setReadOnly(true);
        dialog.add(userName);
        dialog.setHeaderTitle("User details");
        addSampleLine(container, "Dialog", new Button("Open dialog", e-> dialog.open()));

        add(new Paragraph("Windows users: turn \"High Contrast\" on"), container);
    }

    private void addSampleLine(Div container, String label, Component component) {
        var components =  new HorizontalLayout(component);
        //<theme-editor-local-classname>
        components.addClassName("tesrtjcg");
        if (component instanceof HasEnabled) {
            var clone = SerializationUtils.clone(component);
            ((HasEnabled) clone).setEnabled(false);
            components.add(clone);
        }

        if(component instanceof HasValueAndElement<?,?>) {
            var clone = SerializationUtils.clone(component);
            ((HasValueAndElement<?,?>) clone).setReadOnly(true);
            components.add(clone);
        }

        container.add(
            new Span(label),
            components
        );
    }

}
