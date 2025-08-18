import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import static io.restassured.RestAssured.given;

public class ApiTest {

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://users.bugred.ru";
    }

    public static int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    @Test
    public void createPost_shouldReturn201() {


        String emailInput = "test_" + getRandomNumber(100000, 999999) + "@mail.ru";
        String namelInput = "test_" + getRandomNumber(100000, 999999);

        String requestBody = String.format("""
    {
        "email": "%s",
        "name": "%s",
        "password": "2"
    }
    """, emailInput,namelInput);

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

        Assertions.assertEquals(emailInput, email);
    }
}
