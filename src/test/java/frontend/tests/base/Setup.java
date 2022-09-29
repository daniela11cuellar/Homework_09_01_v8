package frontend.tests.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.UnexpectedException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Setup {

    public static WebDriver getDriver(String browser, String url) throws IOException {

        WebDriver driver;

        InputStream file = new FileInputStream("src/main/resources/config.properties");
        Properties properties = new Properties();
        properties.load(file);

        switch(browser) {
            case "chrome":
                System.setProperty(properties.getProperty("PROPERTY_CHROME"), properties.getProperty("PATH_CHROME"));
                driver = new ChromeDriver();
                break;
            case "firefox":
                System.setProperty(properties.getProperty("PROPERTY_FIRE"), properties.getProperty("PATH_FIRE"));
                driver = new FirefoxDriver();
                break;
            case "edge":
                System.setProperty(properties.getProperty("PROPERTY_EDGE"), properties.getProperty("PATH_EDGE"));
                driver = new EdgeDriver();
                break;
            default:
                throw new UnexpectedException("the browser: " + browser + " is not an option");

        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get(url);
        driver.manage().window().maximize();

        return driver;

    }

    public static WebDriverWait  getWait(WebDriver driver, int time) {
        WebDriverWait wait = new WebDriverWait(driver, time);
        return wait;

    }
}
