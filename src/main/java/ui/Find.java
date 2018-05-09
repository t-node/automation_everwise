package ui;

/**
 * Created by vevinmoza on 10/20/17.
 */
public interface Find {
    BrowserElement findById(String id);
    BrowserElement findByName(String name);
    BrowserElement findByLinkText(String linkText);
    BrowserElement findByPartialLinkText(String partialLinkText);
    BrowserElement findByClass(String className);
    BrowserElement findByCssSelector(String cssSelector);
    BrowserElement findByTagName(String tagName);
    BrowserElement findByXpath(String xpath);
}
