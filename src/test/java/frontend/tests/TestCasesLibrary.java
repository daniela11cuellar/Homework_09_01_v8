package frontend.tests;

import frontend.pages.library.DigitalCollections;
import frontend.pages.library.Home;
import frontend.pages.library.Survey;
import frontend.pages.library.TopSearches;
import frontend.utils.Retry;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestCasesLibrary extends BaseTestClass {
    String URL = "https://www.loc.gov/";

    Home home;
    TopSearches topSearches;
    DigitalCollections digitalCollections;
    Survey survey;

    /*
    Test case 1
     */
    @Test(priority=2, groups = {"HomeTestCases"})
    public void verifyThatTheUrlIsTheExpected() {
        home = new Home(driver);
        home.clickOnLogoToBack();
        Assert.assertEquals(home.getUrl(), URL, "The urls aren't the same");
    }
    /*
       Test case 2
        */
    @Test(retryAnalyzer = Retry.class, priority=1, groups = {"HomeTestCases"})
    public void $verifyThatTheCarrouselHasFiveItems() {
        int count = 1;
        int maxPage = 5;

        home = new Home(driver);
        String paging = home.getPaging();

        home.clickOnNextCarrousel();
        home.waitNewCarrousel(count, maxPage);
        count ++;

        Assert.assertEquals(paging, maxPage+"/"+maxPage, "The pagination is not working");
    }
     /*
    Test case 3
     */
    @Test(groups = {"PrevHomeTestCases"}, priority=1)
    public void verifyThatTheTopSearchesWork() {
        home = new Home(driver);
        home.clickRandomTopSearch();

        topSearches = new TopSearches(driver);
        topSearches.validateResultTopSearch();
    }
    /*
       Test case 4 y 6
        */
    @Test(dependsOnGroups = {"PrevHomeTestCases"}, groups = {"HomeTestCases"})
    public void verifyThatTheLinkDigitalCollectionsWorks() {
        home.clickOnDigitalCollections();
        digitalCollections = new DigitalCollections(driver);
        digitalCollections.waitDigitalCollectionPage();
        digitalCollections.assertDigitalCollectionIsPresent();
    }
    /*
         Test case 7
          */
    @Test(dependsOnGroups = {"HomeTestCases"}, dataProvider = "getDataToSearch", groups = {"DigitalCollectionsTestCases"})
    public void verifyThatTheSearchBarWorksInDigitalCollection(String keyWord) {
        digitalCollections.searchKeyWord(keyWord);
        digitalCollections.clickOnSearch();
    }

    @DataProvider
    public Object[][] getDataToSearch() {
        return new Object[][]{
                {"Abraham Lincoln"},
                {"Benjamin Franklin Papers"},
                {"Earth Day"},
        };
    }
    /*
     Test case 8, 9, 10 y 11
      */
    @Test(dependsOnGroups = {"DigitalCollectionsTestCases"}, groups = {"SurveyTestCases"})
    public void verifyTheTakeOurSurveyIsPresent() throws InterruptedException {
        home = new Home(driver);
        home.clickOnLogoToBack();
        home.scrollToSurvey();
        home.clickOnToSurvey();

        survey = new Survey(driver);
        int totalQuestions = survey.getNumberOfQuestions();
        Assert.assertEquals(totalQuestions, 3, "There are not three questions");

        survey.checkWhatYouWereLookingFor();
        survey.checkWhichOptionBestDescribesYou();
        survey.checkHowFamiliarAreYouWith();

        survey.clickOnSubmit();
        survey.validateThanksMessage();
    }

}

