package org.jchristophe.accessibility.utility;

import com.vaadin.flow.component.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jcgueriaud
 */
public class LabelParsingSummary {

    private int labelFoundCount = 0;
    private int labelNotFoundCount = 0;
    private final List<Component> invalidFields = new ArrayList<>();
    private final List<Component> ariaLabelOnlyFields = new ArrayList<>();

    public void incrementLabelFound() {
        labelFoundCount++;
    }

    public void incrementLabelNotFound() {
        labelNotFoundCount++;
    }

    public int getLabelFoundCount() {
        return labelFoundCount;
    }

    public int getLabelNotFoundCount() {
        return labelNotFoundCount;
    }

    public int getAriaLabelFoundCount() {
        return ariaLabelOnlyFields.size();
    }

    public int getInvalidCount() {
        return invalidFields.size();
    }

    public List<Component> getInvalidFields() {
        return invalidFields;
    }

    public void addInvalidField(Component component) {
        invalidFields.add(component);
    }

    public List<Component> getAriaLabelOnlyFields() {
        return ariaLabelOnlyFields;
    }

    public void addAriaLabelField(Component component) {
        ariaLabelOnlyFields.add(component);
    }

}
