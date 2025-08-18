import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

import static io.restassured.RestAssured.given;

public class CreateCompany {

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://users.bugred.ru";
    }

    @Test
    public void createPost_shouldReturn201() {
        String requestBody = """
        {
            "company_name": "Алкоголики и тунеядцы",
            "company_type": "ООО",
            "company_users": ["test_497094@mail.ru", "test_290504@mail.ru"],
            "email_owner": "test_289338@mail.ru"
        }
        """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .log().all()
                .when()
                .post("/tasks/rest/createcompany")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        // Проверка на наличие ошибки от API
        String responseType = response.jsonPath().getString("type");
        if ("error".equalsIgnoreCase(responseType)) {
            String message = response.jsonPath().getString("message");
            Assertions.fail("API вернул ошибку: " + message);
        }

        // Получение названия компании из ответа
        String actualCompanyName = response.jsonPath().getString("company.name");
        Assertions.assertEquals("Алкоголики и тунеядцы", actualCompanyName, "Название компании не совпадает");

        // Проверка пользователей
        List<String> users = response.jsonPath().getList("company.users");
        Assertions.assertNotNull(users, "Список пользователей не должен быть null");
        Assertions.assertFalse(users.isEmpty(), "Список пользователей не должен быть пустым");
    }
}
