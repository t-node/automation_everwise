package initializer;

import org.apache.log4j.Logger;

import ui.BrowserDriver;
import ui.UiBase;
import ui.pages.actions.CommunityPage;
import ui.pages.actions.HomePage;
import ui.pages.actions.LoginPage;

public class PageFactory {
	private static final Logger logger = Logger.getLogger(PageFactory.class);

	public static UiBase getPage(Class pageType, BrowserDriver driver) {

		switch (pageType.getSimpleName()) {

			case "HomePage":
				return new HomePage(driver);
			case "LoginPage":
				return new LoginPage(driver);
			case "CommunityPage":
				return new CommunityPage(driver);
		default:
			return new UiBase();
		}

	}
}
