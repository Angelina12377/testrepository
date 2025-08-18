import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;

public class CreateUserWithTasks {

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://users.bugred.ru";
    }

    public static int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
    @ExtendWith(AllureJunit5.class)
    @Test
    public void createPost_shouldReturn200() {
        String nameInput = "angelina_" + getRandomNumber(100000, 999999);
        String emailInput = "test_" + getRandomNumber(100000, 999999) + "@mail.ru";

        String requestBody = String.format("""
        {
            "email": "%s",
            "name": "%s",
            "tasks": [
                {
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

        // ✅ Проверка имени
        Assertions.assertEquals(nameInput, response.jsonPath().getString("name"), "Имя пользователя в ответе не совпадает с отправленным");

        // ✅ Проверка email
        Assertions.assertEquals(emailInput, response.jsonPath().getString("email"), "Email в ответе не совпадает с отправленным");

        // ✅ Проверка задач
        Assertions.assertEquals("1", response.jsonPath().getString("tasks[0].name"), "Задача 1 в ответе не совпадает с ожидаемой");
        Assertions.assertEquals("2", response.jsonPath().getString("tasks[1].name"), "Задача 2 в ответе не совпадает с ожидаемой");

        // 🔍 (опционально) печать тела ответа
        System.out.println(response.asString());
    }
}
