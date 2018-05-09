package ui.pages.repo;

import org.openqa.selenium.WebElement;
import ui.UiBase;
import ui.pages.ApplicationLabels;
import ui.pages.SortLabels;

/**
 * Created by vevinmoza on 5/5/18.
 */
public class CommunityPageRepo extends UiBase {
    protected WebElement toggleIcon() {
        return findById("questions-filter").findByCssSelector("i[class='Icon icon icon-angle-down toggle-icon']");
    }

    protected WebElement currentFilterTxt() {
        return findById("questions-filter");
    }

    protected WebElement popularQuestionsLnk() {
        return findByLinkText(SortLabels.POPULAR.getLabel());
    }

    protected WebElement recentQuestionsLnk() {
        return findByLinkText(SortLabels.RECENT.getLabel());
    }

}
