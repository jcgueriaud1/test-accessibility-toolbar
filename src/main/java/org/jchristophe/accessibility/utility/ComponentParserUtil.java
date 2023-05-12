package org.jchristophe.accessibility.utility;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.internal.nodefeature.VirtualChildrenList;
import org.jchristophe.accessibility.components.toolbar.AccessibilityToolbar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author jcgueriaud
 */
public class ComponentParserUtil {
    private static final Logger log = LoggerFactory.getLogger(ComponentParserUtil.class);

    public static <T> void applyFunctionOnUI(Class<T> clazz, Consumer<T> applier) {
        applyFunctionRecursively(UI.getCurrent(), clazz, applier);
    }
    private static <T> void applyFunctionRecursively(Component main, Class<T> clazz, Consumer<T> applier) {
        log.debug("applyFunctionRecursively");
            main.getChildren().forEach(c -> {
                log.debug("Child found : " + c.getClass());
                if (!(main instanceof AccessibilityToolbar)) {
                if (clazz.isInstance(c)) {
                    applier.accept((T) c);
                    log.debug("Apply");
                }
                applyFunctionRecursively(c, clazz, applier);
                } else {
                    log.debug("AccessibilityToolbar ignored : " + c.getClass());
                }
            });
    }

    public static LabelParsingSummary validateLabels() {
        LabelParsingSummary labelParsingSummary = new LabelParsingSummary();
        validateLabelRecursively(UI.getCurrent(), labelParsingSummary);
        return labelParsingSummary;
    }
    private static <T> void validateLabelRecursively(Component main, LabelParsingSummary labelParsingSummary) {
        main.getChildren().forEach(c -> {
            log.debug("Child found : " + c.getClass());
                if (c instanceof HasLabel) {
                    String label = ((HasLabel) c).getLabel();
                    if ((label == null) || (label.isEmpty())) {
                        log.debug("Label is empty");
                        labelParsingSummary.incrementLabelNotFound();
                        if (c instanceof HasAriaLabel) {
                            Optional<String> ariaLabel = ((HasAriaLabel) c).getAriaLabel();
                            if (ariaLabel.isEmpty() || ariaLabel.get().isEmpty()) {
                                log.debug("Aria-Label is empty");
                                labelParsingSummary.addInvalidField(c);
                            } else {
                                labelParsingSummary.addAriaLabelField(c);
                            }
                        } else {
                            // no aria and no label
                            labelParsingSummary.addInvalidField(c);
                        }

                    } else {
                        labelParsingSummary.incrementLabelFound();
                    }
                }
            validateLabelRecursively(c, labelParsingSummary);
        });
    }
/*
    private void informChildrenOfStateChange(boolean enabled, Element element) {
        element.getChildren().forEach(child -> {
            informEnabledStateChange(enabled, child);
        });
        if (element.getNode().hasFeature(VirtualChildrenList.class)) {
            element.getNode().getFeatureIfInitialized(VirtualChildrenList.class)
                    .ifPresent(list -> list.forEachChild(
                            node -> informEnabledStateChange(enabled,
                                    Element.get(node))));
        }
    }*/
}
