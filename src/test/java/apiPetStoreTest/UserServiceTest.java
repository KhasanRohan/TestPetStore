package apiPetStoreTest;

import Pojo.CreateNewUser;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserServiceTest {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    @Test//Позитивный тест кейс для GET запроса хэндлера user:
    public void getUserByUsername() {
        Specs.installSpec(Specs.requestSpec(), Specs.responseSpecOK200());
        given()
                .baseUri(BASE_URL)
                .pathParam("username", "TestUser")
                .when()
                .get("/user/{username}")
                .then()
                .assertThat()
                .body("username", equalTo("TestUser"));
    }
    @Test//Негативный тест кейс для GET запроса хэндлера user:
    public void getNonexistentUser() {
        Specs.installSpec(Specs.requestSpec(), Specs.responseSpecUnique(404));
        given()
                .baseUri(BASE_URL)
                .pathParam("username", "nonexistentuser")
                .when()
                .get("/user/{username}")
                .then()
                .assertThat()
                .body("message", equalTo("User not found"));
    }
    @Test// Позитивный тест кейс для POST запроса хэндлера user:
    public void createUser() {
        Specs.installSpec(Specs.requestSpec(), Specs.responseSpecOK200());
        CreateNewUser createNewUser = new CreateNewUser(1, "testuser", "Jhon",
                "Doe", "john.doe@example.com", "password", "1234567890");

        given()
                .baseUri(BASE_URL)
                .body(createNewUser)
                .when()
                .post("/user")
                .then()
                .assertThat()
                .body("message", equalTo("1"));

    }
    @Test//Негативный тест кейс для POST запроса хэндлера user:
    public void createExistingUser() {
        Specs.installSpec(Specs.requestSpec(), Specs.responseError400());
// создаем нового пользователя, в атрибут "username" посместим невалидное значение "\\"
        CreateNewUser createNewUser = new CreateNewUser(1, "\\", "Jane",
                "Doe", "jane.doe@example.com", "password", "1234567890");

        given()
                .baseUri(BASE_URL)
                .body(createNewUser)
                .when()
                .post("/user")
                .then()
                .assertThat()
                .body("message", equalTo("Invalid create"));// ожидаем сообщение об ошибке
// тест упал, потому что в хэндлере User непредусмотрен статус код ошибки (400), вместо этого
// стоит "УСПЕШНАЯ ОПЕРАЦИЯ ПО УМОЛЧАНИЮ"

    }
    @Test//Позитивный тест кейс для DELETE запроса хэндлера user:
    public void deleteUserByUsername() {
        Specs.installSpec(Specs.requestSpec(), Specs.responseSpecOK200());
        given()
                .baseUri(BASE_URL)
                .pathParam("username", "testuser")
                .when()
                .delete("/user/{username}")
                .then()
                .assertThat()
                .body("message", equalTo("testuser"));

    }
    @Test//Негативный тест кейс для DELETE запроса хэндлера user:
    public void deleteNonexistentUser() {
        Specs.installSpec(Specs.requestSpec(), Specs.responseSpecUnique(404));
        given()
                .baseUri(BASE_URL)
                .pathParam("username", "nonexistentuser")
                .when()
                .delete("/user/{username}")
                .then()
                .assertThat();
    }
}
