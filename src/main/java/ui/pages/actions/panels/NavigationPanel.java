package ui.pages.actions.panels;

import ui.BrowserDriver;
import ui.pages.actions.HomePage;
import ui.pages.repo.panelsrepo.NavigationPanelRepo;

/**
 * Created by vevinmoza on 5/5/18.
 */
public class NavigationPanel extends NavigationPanelRepo {
    BrowserDriver driver;

    public NavigationPanel(BrowserDriver driver) {
        this.driver = driver;
        setDriver(driver);
    }

    public HomePage openHomePage() {
        homeLnk().click();
        return new HomePage(driver);
    }

    public HomePage openCommunityPage() {
        communityLnk().click();
        return new HomePage(driver);
    }

}
