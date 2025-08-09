import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
import java.util.List;
import static io.restassured.RestAssured.given;

public class CreateCompany {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://users.bugred.ru";
    }

    @Test
    public void createPost_shouldReturn201() {

        String requestBody = """
        {
            "company_name": "Алкоголики и тунеядцы",
            "company_type": "ООО",
            "company_users": ["test_291064@mail.ru", "test_116509@mail.ru"],
            "email_owner": "test_814128@mail.ru"
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
        response.jsonPath().getString("company.type");
        response.jsonPath().getList("company.users");

        // Проверка имени компании
        Assert.assertEquals("Название компании не совпадает с ожидаемым",
                "Алкоголики и тунеядцы",
                response.jsonPath().getString("company.name"));


        List<String> users = response.jsonPath().getList("company.users");
        Assert.assertNotNull("Список пользователей не должен быть null", users);
        Assert.assertFalse("Список пользователей не должен быть пустым", users.isEmpty());
    }
}