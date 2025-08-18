import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.restassured.RestAssured.given;

public class
MagicSearch {

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://users.bugred.ru";
    }

    @Test
    public void createGet_shouldReturn200() {
        String requestBody = """
            {
                "query": "Алкоголики и тунеядцы"
            }
            """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .log().all()
                .when()
                .post("/tasks/rest/magicsearch")
                .then()
                .log().all()
                .statusCode(235)
                .extract().response();

        System.out.println(response.asString());

        // Извлекаем поле results из ответа
        List<Map<String, Object>> results = response.jsonPath().getList("results");

        // Проверка, что есть как минимум один user и одна company
        boolean hasUser = results.stream().anyMatch(r -> "user".equals(r.get("type")));
        boolean hasCompany = results.stream().anyMatch(r -> "company".equals(r.get("type")));

        Assertions.assertTrue(hasUser, "No users found");
        Assertions.assertTrue(hasCompany, "No companies found");
    }
}
