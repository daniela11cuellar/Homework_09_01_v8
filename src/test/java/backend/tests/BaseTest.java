package backend.tests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    @BeforeClass
    public void setProperties(){
        RestAssured.baseURI = "https://automationintesting.online";
    }
}
