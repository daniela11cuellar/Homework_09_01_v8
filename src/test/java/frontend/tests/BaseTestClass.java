package frontend.tests;

import frontend.tests.base.Setup;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.IOException;

public class BaseTestClass {
    WebDriverWait wait;
    WebDriver driver;

    @BeforeClass
    @Parameters({"browser", "url"})
    public void setup(String browser, String url) throws IOException {
        System.out.println("init driver");
        driver = Setup.getDriver(browser, url);
        wait = Setup.getWait(driver, 10);
    }

    @AfterClass
    public void tearDown(){
        System.out.println("quit driver");
        driver.close();
        driver.quit();
    }
}
