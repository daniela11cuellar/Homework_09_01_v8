package backend.tests;

import backend.data.DataTesting;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static backend.tests.PostCredentialsTest.getToken;
import static backend.tests.RoomGetTest.getRandomIdAvailableRoom;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class PostBookingTest extends BaseTest {
    String userName = "admin";
    String password = "password";

    @Test(dataProvider = "getDataBooking", dataProviderClass = DataTesting.class)
    public void postBooking(String checkin, String checkout, Boolean depositpaid, String firtsname, String lastname, int totalprice) {

        int roomId = getRandomIdAvailableRoom();
        String token = getToken(userName, password);

        HashMap<String, Object> dates = new HashMap();
        dates.put("checkin", checkin);
        dates.put("checkout", checkout);

        HashMap<String, Object> payLoad = new HashMap();

        payLoad.put("bookingdates", dates);
        payLoad.put("depositpaid", depositpaid);
        payLoad.put("firstname", firtsname);
        payLoad.put("lastname", lastname);
        payLoad.put("roomid", roomId);
        payLoad.put("totalprice", totalprice);

        Response response =
            given()
                .contentType(ContentType.JSON)
                .body(payLoad)
                .cookie("token", token)
                .log().all()
            .when()
                .post("/booking/")
            .then()
                .time(lessThan(3000L), TimeUnit.MILLISECONDS)
                .log().all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 201, "The code responses is not the expected");

    }

}
