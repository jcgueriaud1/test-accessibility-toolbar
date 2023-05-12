package org.jchristophe.accessibility.components.toolbar;

import java.io.Serializable;

/**
 * @author jcgueriaud
 */
public class AccessibilityConfiguration implements Serializable {

    private ThemeVariantConfiguration themeVariantConfiguration;
    private FieldStateConfiguration fieldStateConfiguration;

    public void setThemeVariantConfiguration(ThemeVariantConfiguration themeVariantConfiguration) {
        this.themeVariantConfiguration = themeVariantConfiguration;
    }

    public ThemeVariantConfiguration getThemeVariantConfiguration() {
        return themeVariantConfiguration;
    }

    public FieldStateConfiguration getFieldStateConfiguration() {
        return fieldStateConfiguration;
    }

    public void setFieldStateConfiguration(FieldStateConfiguration fieldStateConfiguration) {
        this.fieldStateConfiguration = fieldStateConfiguration;
    }
}
