package backend.data;

public class DataTesting {

    @org.testng.annotations.DataProvider
    public Object[][] getDataBooking() {
        return new Object[][]{
                {"2024-07-01T09:48:25.469Z", "2024-07-04T09:48:25.469Z", true, "FirstName1", "LastName1", 0},
                {"2024-07-02T09:48:25.469Z", "2024-07-05T09:48:25.469Z", true, "FirstName2", "LastName2", 0},
                {"2024-07-03T09:48:25.469Z", "2024-07-06T09:48:25.469Z", true, "FirstName3", "LastName3", 0},
        };

    }
}