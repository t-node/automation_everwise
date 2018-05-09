package ui.pages.repo;

import org.openqa.selenium.WebElement;
import ui.BrowserElement;
import ui.UiBase;

public class LoginPageRepo extends UiBase {

    protected WebElement emailTxtBox() {
        return findById("user_email");
    }

    protected WebElement passwordTxtBox() {
        return findById("user_password");
    }

    protected WebElement signInBtn() {
        return findByName("button");
    }


}
