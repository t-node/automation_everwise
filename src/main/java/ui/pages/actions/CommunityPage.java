package ui.pages.actions;

import ui.BrowserDriver;
import ui.pages.ApplicationLabels;
import ui.pages.SortLabels;
import ui.pages.actions.panels.CommunityQuestionPreviewPanels;
import ui.pages.actions.panels.NavigationPanel;
import ui.pages.repo.CommunityPageRepo;

/**
 * Created by vevinmoza on 5/5/18.
 */
public class CommunityPage extends CommunityPageRepo {

    BrowserDriver driver;
    NavigationPanel navigationPanel;
    CommunityQuestionPreviewPanels communityQuestionPreviewPanels;


    public CommunityPage(BrowserDriver driver) {
        this.driver = driver;
        navigationPanel = new NavigationPanel(driver);
        communityQuestionPreviewPanels = new CommunityQuestionPreviewPanels(driver);
        setDriver(driver);
    }

    public String getAnswerCountForQuestion(int panelIndex) {
        return communityQuestionPreviewPanels.getAnswerCountForQuestion(panelIndex);
    }
    public boolean isCommunityPageLoaded(){
        return communityQuestionPreviewPanels.isPanelLoaded(0);
    }

    public String getViewCountForQuestion(int panelIndex) {
        return communityQuestionPreviewPanels.getViewCountForQuestion(panelIndex);
    }

    public CommunityPage sortBy(SortLabels sortType) {
        if (!currentFilterTxt().getText().contains(sortType.getLabel())) {
            toggleIcon().click();
            switch (sortType) {
                case POPULAR:
                    popularQuestionsLnk().click();
                    break;
                case RECENT:
                    recentQuestionsLnk().click();
                    break;
                default:
                    recentQuestionsLnk().click();
            }
        }
        return this;
    }

    public HomePage openHomePage() {
        navigationPanel.openHomePage();
        return new HomePage(driver);
    }
}
