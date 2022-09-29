package frontend.tests;

import frontend.tests.base.Setup;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.IOException;

public class BaseTestMethod {
    WebDriverWait wait;
    WebDriver driver;

    @BeforeMethod
    @Parameters({"browser", "url"})
    public void setup(String browser, String url) throws IOException {
        driver = Setup.getDriver(browser, url);
        wait = Setup.getWait(driver, 10);
    }

    @AfterMethod
    public void tearDown(){
        driver.close();
    }
}
