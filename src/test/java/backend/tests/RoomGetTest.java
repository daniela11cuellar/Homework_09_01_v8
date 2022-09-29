package backend.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;


public class RoomGetTest extends BaseTest {

    public static List<HashMap<String, Object>> getRooms() {

        Response response = given()
                .contentType(ContentType.JSON)
        .when()
                .get("/room/")
        .then()
                //.time(lessThan(6000L), TimeUnit.MILLISECONDS)
                .extract().response();

        Assert.assertEquals(response.statusCode(), 200, "The code responses is not the expected");
        return response.jsonPath().getList("rooms");

    }

    @Test
    public void testFieldsOfRoomsResponse() {
        List<HashMap<String, Object>> listRooms = getRooms();
        Assert.assertTrue(listRooms.size()>0, "There are not rooms");
        SoftAssert softAssert = new SoftAssert();

        for(HashMap mapFieldsRoom : listRooms){
            softAssert.assertEquals(mapFieldsRoom.get("roomid").getClass(), Integer.class, "The `roomid` is not a number");
            softAssert.assertEquals(mapFieldsRoom.get("roomName").getClass(), String.class, "The `roomName` is not a string");
            softAssert.assertEquals(mapFieldsRoom.get("type").getClass(), String.class, "The `type` is not a string");
            softAssert.assertEquals(mapFieldsRoom.get("accessible").getClass(), Boolean.class, "The `accessible` is not a boolean");
            softAssert.assertTrue(mapFieldsRoom.get("image").toString().contains(".jpg") && mapFieldsRoom.get("image").toString().contains("https://"),
                    "The `image` is not an url valid");
            softAssert.assertEquals(mapFieldsRoom.get("description").getClass(), String.class, "The `description` is not a string");
            softAssert.assertEquals(mapFieldsRoom.get("features").getClass(), ArrayList.class, "The `features` is not an array");
            softAssert.assertEquals(mapFieldsRoom.get("roomPrice").getClass(), Integer.class, "The `roomPrice` is not a number");
        }
        softAssert.assertAll();
    }

    public static List<HashMap<String, Object>> getAccessibleRooms() {
        List<HashMap<String, Object>> listAccessibleRooms = getRooms();
        return listAccessibleRooms.stream()
                .filter(map -> map.get("accessible").equals(true)).collect(Collectors.toList());
    }

    public static int getRandomIdAvailableRoom(){
        List<HashMap<String, Object>> listAccessibleRooms = getAccessibleRooms();
        int randomAccessibleRooms = ThreadLocalRandom.current().nextInt(0, listAccessibleRooms.size());
        return (int) listAccessibleRooms.get(randomAccessibleRooms).get("roomid");
    }

}
