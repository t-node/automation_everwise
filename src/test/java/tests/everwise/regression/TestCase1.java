package tests.everwise.regression;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import initializer.BaseTest;
import ui.pages.ApplicationLabels;
import ui.pages.SortLabels;
import ui.utils.RegexSearch;
import ui.utils.RegularExpression;

/**
 * Created by vevinmoza on 3/18/16.
 */

public class TestCase1 extends BaseTest {
    private static final Logger logger = Logger.getLogger(TestCase1.class);

    @Test(alwaysRun = true, description = "View count of popular post should be greater than recent post", groups = {
            "regression"})
    public void PopularPostAnswersAndViewsCountGreaterThanRecentPostAnswersAndViewsCount() throws Exception {

        //Login to the application
        loginPage.signIn(getData("TC1.EMAIL"), getData("TC1.PASSWORD"));
        Assert(homePage.isHomePageLoaded(), "Login " +
                "Successful with email as: " + getData("TC1.EMAIL") + "and password as: " + getData("TC1.PASSWORD"));

        //opening the Community Page
        homePage.openCommunityPage();
        Assert(communityPage.isCommunityPageLoaded(), "Community Page has successfully loaded");


        //store the answers and view count in a hashmap for later comparison
        addToStorage("recentQuestionAnswerCount", communityPage.getAnswerCountForQuestion(0));
        addToStorage("recentQuestionViewCount", communityPage.getViewCountForQuestion(0));


        //validate the presence and numeric value of views and answers
        Assert(getFromStorage("recentQuestionAnswerCount").
                matches(RegularExpression.NUMBER + " Answers"), "Numeric answers count is present");
        Assert(getFromStorage("recentQuestionViewCount")
                .matches(RegularExpression.NUMBER + " Views"), "Numeric view count is present");


        //Sort questions by Popular
        communityPage.sortBy(SortLabels.POPULAR);


        //Checking the answers and view count is greater for popular post than recent post
        Assert(RegexSearch.getInteger(RegularExpression.NUMBER,communityPage.getAnswerCountForQuestion(0))>
                RegexSearch.getInteger(RegularExpression.NUMBER,getFromStorage("recentQuestionAnswerCount")),
                "Popular Answers count is greater than Recent Answers count");
        Assert(RegexSearch.getInteger(RegularExpression.NUMBER,communityPage.getViewCountForQuestion(0))>
                RegexSearch.getInteger(RegularExpression.NUMBER,getFromStorage("recentQuestionViewCount")),
                "Popular Views count is greater than Recent Views count");

    }
}
