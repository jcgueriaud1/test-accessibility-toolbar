package org.jchristophe.accessibility.components.toolbar;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;

/**
 * @author jcgueriaud
 */
@JavaScript("https://unpkg.com/accessibility-checker-engine@latest/ace.js")
@NpmPackage(value = "accessibility-checker-engine", version = "3.1.49")
@Tag("dummy-accessibility-devtools")
@JsModule("./accessibility-devtools-loader.ts")
public class AccessibilityDevToolsLoader extends Component {
}
