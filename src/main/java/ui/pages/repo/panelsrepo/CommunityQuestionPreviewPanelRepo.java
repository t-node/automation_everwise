package ui.pages.repo.panelsrepo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ui.BrowserElement;
import ui.UiBase;

/**
 * Created by vevinmoza on 5/5/18.
 */
public class CommunityQuestionPreviewPanelRepo extends UiBase {

    protected WebElement answerCount(int index) {
        return getDriver().findElements(By.cssSelector("span[class='IconCounter CommunityQuestionMetadata--answer-count']")).get(index);
    }

    protected WebElement viewCount(int index) {
        return getDriver().findElements(By.cssSelector("span[class='IconCounter']")).get(index);
    }
}
