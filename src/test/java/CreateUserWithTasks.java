import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class CreateUserWithTasks {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://users.bugred.ru";
    }
    public static int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    @Test
    public void createPost_shouldReturn200() {

        String nameInput = "angelina_" + CreateUserWithTasks.getRandomNumber(100000, 999999);

        String emailInput = "test_" + CreateUserWithTasks.getRandomNumber(100000, 999999) + "@mail.ru";

        String requestBody = String.format("""

        {
        "email": "%s",
        "name": "%s",
        "tasks": [{
        "title": "1",
        "description": "123"
  },
  {
    "title": "2",
    "description": "1234"
  }
 ]
}
    """, emailInput, nameInput);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .log().all()
                .when()
                .post("/tasks/rest/createuserwithtasks")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        System.out.println(response.asString());

        // ✅ Проверка имени
        String actualName = response.jsonPath().getString("name");
        Assert.assertEquals("Имя пользователя в ответе не совпадает с отправленным", nameInput, actualName);

        // ✅ Проверка email
        String actualEmail = response.jsonPath().getString("email");
        Assert.assertEquals("Email в ответе не совпадает с отправленным", emailInput, actualEmail);

        // Проверка задач
        String task1Title = response.jsonPath().getString("tasks[0].name");
        Assert.assertEquals("Задача 1 в ответе не совпадает с ожидаемой", "1", task1Title);


        String task2Title = response.jsonPath().getString("tasks[1].name");
        Assert.assertEquals("Задача 2 в ответе не совпадает с ожидаемой", "2", task2Title);
    }
}
