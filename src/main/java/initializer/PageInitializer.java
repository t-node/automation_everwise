package initializer;

import org.apache.log4j.Logger;

import ui.BrowserDriver;
import ui.pages.actions.CommunityPage;
import ui.pages.actions.HomePage;
import ui.pages.actions.LoginPage;

public class PageInitializer extends BrowserInitializer {
    private static final Logger logger = Logger.getLogger(PageInitializer.class);

    protected HomePage homePage;
    protected LoginPage loginPage;
    protected CommunityPage communityPage;

    public static int counter;

    public void initializePages(BrowserDriver driver, Class testClass) {
        logger.info("CLASS BEING TESTED :" + testClass + " " + Thread.currentThread().getStackTrace()[1] + "ThreadID: "
                + Thread.currentThread().getId() + "  " + Thread.currentThread().getName() + " "
                + "Begin instantiating page objects");
        homePage = (HomePage) PageFactory
                .getPage(HomePage.class, driver);
        loginPage = (LoginPage) PageFactory.getPage(LoginPage.class, driver);
        communityPage = (CommunityPage) PageFactory.getPage(CommunityPage.class, driver);
        logger.info("ThreadID: " + Thread.currentThread().getId() + "  " + Thread.currentThread().getName() + " "
                + "Done instantiating page objects");
    }
}
