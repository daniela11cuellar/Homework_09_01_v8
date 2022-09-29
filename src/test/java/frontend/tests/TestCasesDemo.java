package frontend.tests;

import frontend.pages.demoqa.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestCasesDemo extends BaseTestMethod {

    Home home;
    Forms forms;
    PracticeForm practiceForm;
    SelectDatePage selectDatePage;
    ModalPage modal;

    @Test
    public void testHome() {
        home = new Home(driver);

        home.removeAds();
        home.clickBtnForms();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("main-header")));
        Assert.assertEquals(driver.getCurrentUrl(), "https://demoqa.com/forms", "The urls aren't the same");
    }

    @Test
    public void testForms() {
        testHome();
        forms = new Forms(driver);
        forms.clickBtnPractice();

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.className("practice-form-wrapper")));

        Assert.assertEquals(driver.getCurrentUrl(), "https://demoqa.com/automation-practice-form", "The urls aren't the same");
    }

    @Test(dataProvider = "getData")
    public void testPracticeForm(String name, String lastName, String email,
                                 String mobile, String subject, String address, String state, String city) {

        testForms();

        practiceForm = new PracticeForm(driver);
        practiceForm.typeFirstName(name);
        practiceForm.typeLastName(lastName);
        practiceForm.typeEmail(email);
        practiceForm.typeMobile(mobile);
        practiceForm.typeSubject(subject);
        practiceForm.typeAddress(address);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(practiceForm.getFirstName(), name, "The name isn't the expected");
        softAssert.assertEquals(practiceForm.getLastName(), lastName, "The last name isn't the expected");
        softAssert.assertEquals(practiceForm.getEmail(), email, "The email isn't the expected");
        softAssert.assertEquals(practiceForm.getMobile(), mobile, "The mobile isn't the expected");
        softAssert.assertEquals(practiceForm.getSubject(), subject, "The subject isn't the expected");
        softAssert.assertEquals(practiceForm.getAddress(), address, "The address isn't the expected");
        softAssert.assertAll();

        String gender = practiceForm.clickBtnGender();
        practiceForm.clickBirth();

        selectDatePage = new SelectDatePage(driver);

        String month = selectDatePage.selectMonth();
        String year = selectDatePage.selectYear();

        String day = "20";
        selectDatePage.selectDay(day);

        practiceForm.assertDate(day, month, year);
        String hobby = practiceForm.clickBtnHobbies();

        String path = "C:\\Users\\user.DESKTOP-0I3ILBP\\Desktop\\";
        String file = "testing.jpg";
        practiceForm.uploadImage(path+file);

        practiceForm.selectState(state);
        practiceForm.selectCity(city);

        practiceForm.clickSubmit();        modal = new ModalPage(driver);
        modal.assertModalIsPresent();
        modal.assertInformation(name, lastName, email, gender, mobile, day, month, year,
                subject, address, hobby, file, state, city);
    }


    @DataProvider
    public Object[][] getData() {
        return new Object[][]{
                {"Robot", "Executor", "robot-executor@hotmail.com", "3219790624", "Maths", "Kra 6 A este", "NCR", "Delhi"},
        };
    }


    @AfterMethod
    public void tearDown(){
        driver.close();
    }
}

