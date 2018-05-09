package ui.pages.repo.panelsrepo;

import org.openqa.selenium.WebElement;
import ui.BrowserElement;
import ui.UiBase;
import ui.pages.ApplicationLabels;

/**
 * Created by vevinmoza on 5/5/18.
 */
public class NavigationPanelRepo extends UiBase {
    protected WebElement homeLnk() {
        return findByLinkText(ApplicationLabels.HOME.getLabel());
    }

    protected WebElement communityLnk() {
        return findByLinkText(ApplicationLabels.COMMUNITY.getLabel());
    }
}
