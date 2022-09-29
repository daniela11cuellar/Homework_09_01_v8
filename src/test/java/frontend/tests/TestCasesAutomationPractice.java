package frontend.tests;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TestCasesAutomationPractice extends BaseTestMethod {

    String baseUrl = "https://rahulshettyacademy.com/AutomationPractice/";

    @Test
    public void testOne () {

        //random
        int min = 1;
        int max = 3;

        int index = ThreadLocalRandom.current().nextInt(min, max+1);
        System.out.println("random: "+index);

        //get radios
        List<WebElement> options = driver.findElements(By.name("radioButton")) ;
        System.out.println("options: "+options.size());

        options.get(index-1).click();

        for (int i=0; i<options.size(); i++ ) {
            String value = options.get(i).getAttribute("value");
            if (options.get(i).isSelected()) {
                System.out.println("Selected RadioButton: " + value);
            } else {
                System.out.println("NOT Selected RadioButton: " + value);
            }
        }

    }

    @Test
    public void testTwo () throws InterruptedException {
        WebElement input = driver.findElement(By.id("autocomplete"));
        String placeholder = input.getAttribute("placeholder");
        System.out.println(placeholder);
        input.sendKeys("El Sal");

        WebElement country = driver.findElement(By.className("ui-menu-item-wrapper"));
        country.click();
        String nameCountry = input.getAttribute("value");
        System.out.println(nameCountry);
        }

    @Test
    public void testThree () throws InterruptedException {
         Select select = new Select(driver.findElement(By.id("dropdown-class-example")));
         select.selectByValue("option2");
         System.out.println(select.getAllSelectedOptions().get(0).getAttribute("value"));
         WebElement otherOption = select.getOptions().get(3);
         String option3 = otherOption.getAttribute("value");
         System.out.println(option3);
         otherOption.click();
    }

    @Test
    public void testFour() throws InterruptedException {
        WebElement header = driver.findElement(By.xpath("/html/body/header"));
        List<WebElement> botons = header.findElements(By.className("btn-primary"));
        System.out.println("botons: "+botons.size());


        for(int i=0; i<botons.size(); i++){

            botons.get(i).click();
            String url = driver.getCurrentUrl();
            System.out.println(url);

            if(url.equals(baseUrl)){
                System.out.println("Same URL - Btn: " + botons.get(i).getAttribute("textContent"));
            }else{
                driver.navigate().back();
                header = driver.findElement(By.xpath("/html/body/header"));
                botons = header.findElements(By.className("btn-primary"));
                System.out.println("Different URL - Btn: " + botons.get(i).getAttribute("textContent"));
            }
        }

        Thread.sleep(3000);
    }

    @Test
    public void testFive() throws InterruptedException {
        WebElement header = driver.findElement(By.xpath("/html/body/header"));
        List<WebElement> botons = header.findElements(By.className("btn-primary"));
        System.out.println("botons: "+botons.size());
        int totalTabs= 0;

        for(int i=0; i<botons.size(); i++){
            botons.get(i).click();
            String url = driver.getCurrentUrl();
            System.out.println(url);

            if(!url.equals(baseUrl)){
                driver.navigate().back();
                while(totalTabs<8){
                    ((JavascriptExecutor)driver).executeScript("window.open()");
                    totalTabs++;

                    ArrayList<String> tabs = new ArrayList(driver.getWindowHandles());
                    driver.switchTo().window(tabs.get(totalTabs));
                    driver.get(url);
                    Thread.sleep(1000);
                    driver.switchTo().window(tabs.get(0));

                }
                System.out.println("Tabs: " + driver.getWindowHandles().size());
                break;
            }
        }

    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }


}
