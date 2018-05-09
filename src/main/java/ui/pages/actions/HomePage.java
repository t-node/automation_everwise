package ui.pages.actions;

import ui.BrowserDriver;
import ui.pages.actions.panels.NavigationPanel;
import ui.pages.repo.HomePageRepo;

/**
 * Created by vevinmoza on 5/5/18.
 */
public class HomePage extends HomePageRepo {

    BrowserDriver driver;
    NavigationPanel navigationPanel;
    public HomePage(BrowserDriver driver) {
        this.driver = driver;
        navigationPanel= new NavigationPanel(driver);
        setDriver(driver);
    }
    public CommunityPage openCommunityPage(){
        navigationPanel.openCommunityPage();
        return new CommunityPage(driver);
    }

    public boolean isHomePageLoaded(){
        return explorePathLnk().isDisplayed() && explorePathLnk().isEnabled();
    }



}
