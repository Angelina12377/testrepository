import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
import org.json.JSONObject;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateCompany {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://users.bugred.ru";
    }

    public static int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    @Test
    public void createPost_shouldReturn200() {



        String requestBody;
        requestBody = """
    {
        "company_name": "Алкоголики и тунеядцы",
        "company_type": "ООО",
        "company_users": ["te12st_cu_214@mail.com", "te12st_cu_21@mail.com"],
        "email_owner": "aa+1@mail.com"
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

        System.out.println("Response Body: " + response.asString());
        String companyName = response.jsonPath().getString("company_name");
        Assert.assertEquals("Название компании не совпадает с ожидаемым", "Алкоголики и тунеядцы", companyName);

        String companyType = response.jsonPath().getString("company_type");
        Assert.assertEquals("Тип компании не совпадает с ожидаемым", "ООО", companyType);

        String emailOwner = response.jsonPath().getString("email_owner");
        Assert.assertEquals("Email владельца не совпадает с ожидаемым", "aa+1@mail.com", emailOwner);

        // Дополнительно можно проверить, что массив пользователей не пуст
        List<String> companyUsers = response.jsonPath().getList("company_users");
        Assert.assertNotNull("Список пользователей не должен быть пустым", companyUsers);
        Assert.assertTrue("Список пользователей должен содержать хотя бы одного пользователя", companyUsers.size() > 0);

    }
}
