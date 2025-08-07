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



        String requestBody = """

        {
        "email": "te12st_cu_214@mail.com",
        "name": "АнгелинаТест111",
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
    """;

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


        // Проверка email в ответе
        String responseEmail = response.jsonPath().getString("email");
        Assert.assertEquals("Email в ответе не совпадает с ожидаемым", "te12st_cu_214@mail.com", responseEmail);

        // Проверка имени пользователя
        String responseName = response.jsonPath().getString("name");
        Assert.assertEquals("Имя в ответе не совпадает с ожидаемым", "АнгелинаТест111", responseName);

        // Проверка задач
        String task1Title = response.jsonPath().getString("tasks[0].name");
        Assert.assertEquals("Задача 1 в ответе не совпадает с ожидаемой", "1", task1Title);

        String task2Title = response.jsonPath().getString("tasks[1].name");
        Assert.assertEquals("Задача 2 в ответе не совпадает с ожидаемой", "2", task2Title);
    }
}
