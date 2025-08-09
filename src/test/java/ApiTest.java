import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
import static io.restassured.RestAssured.given;

public class ApiTest {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://users.bugred.ru";
    }

    public static int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    @Test
    public void createPost_shouldReturn201() {


        String emailInput = "test_" + getRandomNumber(100000, 999999) + "@mail.ru";

        String requestBody = String.format("""
    {
        "email": "%s",
        "name": "123Ангели43345h5232335452342",
        "password": "2"
    }
    """, emailInput);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .log().all()
                .when()
                .post("/tasks/rest/doregister")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
        String email = response.jsonPath().getString("email");

        Assert.assertEquals(emailInput, email);
    }
}
