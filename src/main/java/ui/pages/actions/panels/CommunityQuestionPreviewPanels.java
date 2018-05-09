package ui.pages.actions.panels;

import ui.BrowserDriver;
import ui.UiBase;
import ui.pages.actions.HomePage;
import ui.pages.repo.panelsrepo.CommunityQuestionPreviewPanelRepo;

/**
 * Created by vevinmoza on 5/5/18.
 */
public class CommunityQuestionPreviewPanels extends CommunityQuestionPreviewPanelRepo {
    BrowserDriver driver;

    public CommunityQuestionPreviewPanels(BrowserDriver driver) {
        this.driver = driver;
        setDriver(driver);
    }

    public String getAnswerCountForQuestion(int panelIndex) {
        return answerCount(panelIndex).getText();
    }

    public boolean isPanelLoaded(int panelIndex) {
        return answerCount(panelIndex).isDisplayed() && answerCount(panelIndex).isEnabled();
    }

    public String getViewCountForQuestion(int panelIndex) {
        return viewCount(panelIndex).getText();
    }


}

