package backend.data;

public class DataTesting {

    @org.testng.annotations.DataProvider
    public Object[][] getDataBooking() {
        return new Object[][]{
                {"2027-07-01T09:48:25.469Z", "2027-07-04T09:48:25.469Z", true, "FirstName1", "LastName1", 0},
                {"2027-07-05T09:48:25.469Z", "2027-07-10T09:48:25.469Z", true, "FirstName2", "LastName2", 0},
                {"2027-07-11T09:48:25.469Z", "2027-07-15T09:48:25.469Z", true, "FirstName3", "LastName3", 0},
        };

    }
}