package ui.pages.repo;

import org.openqa.selenium.WebElement;
import ui.UiBase;
import ui.pages.ApplicationLabels;

/**
 * Created by vevinmoza on 5/5/18.
 */
public class HomePageRepo extends UiBase {
    protected WebElement explorePathLnk() {
        return findByLinkText(ApplicationLabels.EXPLOREPATH.getLabel());
    }
}
