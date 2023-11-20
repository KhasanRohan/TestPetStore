package apiPetStoreTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;

public class PetStoreApiTest {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    @Test//Позитивный тест-кейс для get запроса хэндлер Pet:
    public void testGetPetById_Positive() {
        int petId = 1; // Идентификатор существующего питомца

        given()
                .baseUri(BASE_URL)
                .when()
                .get("/pet/{petId}", petId)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .prettyPrint();
    }

    @Test//Негативный тест-кейс для get запроса хэндлер Pet:
    public void testGetPetById_Negative() {
        int petId = 9999; // Идентификатор несуществующего питомца

        given()
                .baseUri(BASE_URL)
                .when()
                .get("/pet/{petId}", petId)
                .then()
                .statusCode(404)
                .extract()
                .response()
                .prettyPrint();
    }
    @Test//Позитивный тест-кейс для post запроса хэндлер Pet:
    public void testCreateNewPet() {
        // подготовка данных для запроса
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "Rex");
        requestParams.put("status", "available");

        // отправка post запроса
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestParams.toString())
                .when()
                .post("https://petstore.swagger.io/v2/pet");

        // проверка статус кода и тела ответа
        response.then()
                .assertThat()
                .statusCode(200)
                .body("name", equalTo("Rex"))
                .body("status", equalTo("available"));
    }
    @Test//Негативный тест-кейс для post запроса хэндлер Pet:
    public void testCreateNewPetWithInvalidData() {
        // подготовка данных для запроса
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "");
        requestParams.put("status", "invalid_status");

        // отправка post запроса
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestParams.toString())
                .when()
                .post("https://petstore.swagger.io/v2/pet");

        // проверка статус кода и тела ответа
        response.then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("invalid input"));
    }
    @Test//Позитивный тест кейс для delete запроса хэндлер Pet:
    public void deletePetWithValidId() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        // Создаем новое животное
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{\"id\": 1, \"name\": \"doggie\", \"photoUrls\": []}")
                .when()
                .post("/pet")
                .then()
                .extract().response();

        // Удаляем животное с указанным id
        given()
                .pathParam("petId", 1)
                .when()
                .delete("/pet/{petId}")
                .then()
                .assertThat()
                .statusCode(200);

        // Проверяем, что животное удалено
        given()
                .pathParam("petId", 1)
                .when()
                .get("/pet/{petId}")
                .then()
                .assertThat()
                .statusCode(404);
    }
    @Test//Негативный тест кейс для delete запроса хэндлер Pet:
    public void deleteNonexistentPet() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        // Удаляем животное с несуществующим id
        given()
                .pathParam("petId", 999)
                .when()
                .delete("/pet/{petId}")
                .then()
                .assertThat()
                .statusCode(404)
                .body("message", is("Pet not found"));
    }
    @Test//Позитивный тест кейс для GET запроса хэндлера store:
    public void getStoreInventory() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        given()
                .when()
                .get("/store/inventory")
                .then()
                .assertThat()
                .statusCode(200)
                .body("pets.available", is(100)); // Предположим, что в данном случае ожидаемый результат - 100 доступных животных в магазине
    }
    @Test//Негативный тест кейс для GET запроса хэндлера store:
    public void getNonexistentStoreInventory() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        given()
                .when()
                .get("/store/nonexistentEndpoint") // Предполагается, что "nonexistentEndpoint" не существует
                .then()
                .assertThat()
                .statusCode(404);
    }
    @Test//Позитивный тест кейс для POST запроса хэндлера store:
    public void addNewOrderToStore() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        String requestBody = "{\"id\": 1, \"petId\": 1, \"quantity\": 1, \"shipDate\": \"2022-01-01\", \"status\": \"placed\", \"complete\": true}";

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/store/order")
                .then()
                .assertThat()
                .statusCode(200)
                .body("message", is("1"));
    }
    @Test//Негативный тест кейс для POST запроса хэндлера store:
    public void addInvalidOrderToStore() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        String requestBody = "{\"id\": \"InvalidID\", \"petId\": 1, \"quantity\": 1, \"shipDate\": \"2022-01-01\", \"status\": \"placed\", \"complete\": true}";

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/store/order")
                .then()
                .assertThat()
                .statusCode(400);
    }
    @Test//Позитивный тест кейс для DELETE запроса хэндлера store:
    public void deleteOrderFromStore() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        given()
                .pathParam("orderId", 1)
                .when()
                .delete("/store/order/{orderId}")
                .then()
                .assertThat()
                .statusCode(200);
    }
    @Test//Негативный тест кейс для DELETE запроса хэндлера store:
    public void deleteNonexistentOrderFromStore() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        given()
                .pathParam("orderId", 999)
                .when()
                .delete("/store/order/{orderId}")
                .then()
                .assertThat()
                .statusCode(404);
    }
    @Test//Позитивный тест кейс для GET запроса хэндлера user:
    public void getUserByUsername() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        given()
                .pathParam("username", "testuser")
                .when()
                .get("/user/{username}")
                .then()
                .assertThat()
                .statusCode(200)
                .body("username", is("testuser"));
    }
    @Test//Негативный тест кейс для GET запроса хэндлера user:
    public void getNonexistentUser() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        given()
                .pathParam("username", "nonexistentuser")
                .when()
                .get("/user/{username}")
                .then()
                .assertThat()
                .statusCode(404);
    }
    @Test// Позитивный тест кейс для POST запроса хэндлера user:
    public void createUser() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        String requestBody = "{\n" +
                "  \"id\": 100,\n" +
                "  \"username\": \"testuser\",\n" +
                "  \"firstName\": \"John\",\n" +
                "  \"lastName\": \"Doe\",\n" +
                "  \"email\": \"john.doe@example.com\",\n" +
                "  \"password\": \"password\",\n" +
                "  \"phone\": \"1234567890\"\n" +
                "}";

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/user")
                .then()
                .assertThat()
                .statusCode(200);
    }
    @Test//Негативный тест кейс для POST запроса хэндлера user:

    public void createExistingUser() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        String requestBody = "{\n" +
                "  \"id\": 100,\n" +
                "  \"username\": \"testuser\",\n" +
                "  \"firstName\": \"Jane\",\n" +
                "  \"lastName\": \"Doe\",\n" +
                "  \"email\": \"jane.doe@example.com\",\n" +
                "  \"password\": \"password\",\n" +
                "  \"phone\": \"1234567890\"\n" +
                "}";

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/user")
                .then()
                .assertThat()
                .statusCode(400);
    }
    @Test//Позитивный тест кейс для DELETE запроса хэндлера user:
    public void deleteUserByUsername() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        given()
                .pathParam("username", "testuser")
                .when()
                .delete("/user/{username}")
                .then()
                .assertThat()
                .statusCode(200);
    }
    @Test//Негативный тест кейс для DELETE запроса хэндлера user:
    public void deleteNonexistentUser() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        given()
                .pathParam("username", "nonexistentuser")
                .when()
                .delete("/user/{username}")
                .then()
                .assertThat()
                .statusCode(404);
    }
}


