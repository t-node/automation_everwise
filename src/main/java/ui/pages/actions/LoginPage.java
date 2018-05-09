package ui.pages.actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.support.ui.Select;

import ui.BrowserDriver;
import ui.pages.repo.LoginPageRepo;


public class LoginPage extends LoginPageRepo {
    BrowserDriver driver;

    public LoginPage(BrowserDriver driver) {
        this.driver = driver;
        setDriver(driver);
    }

    public HomePage signIn(String... parameters) {
        emailTxtBox().sendKeys(parameters[0]);
        passwordTxtBox().sendKeys(parameters[1]);
        signInBtn().click();
        return new HomePage(driver);
    }


}
