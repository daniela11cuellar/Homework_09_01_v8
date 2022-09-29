package backend.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class PostCredentialsTest extends BaseTest {

    String userName = "admin";
    String password = "password";

    @Test
    public void testGetToken() {
        String token = getToken(userName, password);
        Assert.assertNotEquals(token, null, "The token is null");
    }

    public static String getToken(String username, String password) {

        JSONObject credentials = new JSONObject();
        credentials.put("username", username);
        credentials.put("password", password);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(credentials.toString())
                .log().all()
        .when()
                .post("/auth/login")
        .then()
                .log().all()
                .time(lessThan(2000L), TimeUnit.MILLISECONDS)
                .extract().response();

        Assert.assertEquals(response.statusCode(), 200, "The code responses is not the expected");
        return response.cookie("token");

    }
}
