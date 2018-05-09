package ui.utils.browser;

public enum Browser {
    HTMLUNIT("default"),
    FIREFOX("firefox"),
    CHROME("chrome");

    private String browserName;

    Browser(String browserName) {
        this.browserName = browserName;
    }

    public String getName() {
        return this.browserName;
    }

    public static Browser getBrowser(String name) {
        for (Browser browser : values()) {
            String browserName = browser.getName();
            if (browserName.equalsIgnoreCase(name)) {
                return browser;
            }
        }
        return HTMLUNIT;
    }
}