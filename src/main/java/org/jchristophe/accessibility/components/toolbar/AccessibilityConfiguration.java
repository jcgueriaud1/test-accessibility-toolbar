package org.jchristophe.accessibility.components.toolbar;

import java.io.Serializable;

/**
 * @author jcgueriaud
 */
public class AccessibilityConfiguration implements Serializable {

    private ThemeVariantConfiguration themeVariantConfiguration;

    public void setThemeVariantConfiguration(ThemeVariantConfiguration themeVariantConfiguration) {
        this.themeVariantConfiguration = themeVariantConfiguration;
    }

    public ThemeVariantConfiguration getThemeVariantConfiguration() {
        return themeVariantConfiguration;
    }
}
