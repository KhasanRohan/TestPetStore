package apiPetStoreTest;

import Pojo.CreateNewPet;
import io.restassured.http.ContentType;
import org.hamcrest.Matcher;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class PetServiceTest {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    @Test//Позитивный тест-кейс для get запроса хэндлер Pet:
    public void testGetPetById_Positive() {
        Specs.installSpec(Specs.requestSpec(), Specs.responseSpecOK200());
        int petId = 9; // Идентификатор существующего питомца

        given()
                .baseUri(BASE_URL).
        when()
                .get("/pet/{petId}", petId).
        then()
                .extract()
                .response()
                .prettyPrint();
    }

    @Test//Негативный тест-кейс для get запроса хэндлер Pet:
    public void testGetPetById_Negative() {
        Specs.installSpec(Specs.requestSpec(), Specs.responseSpecUnique(404));
        int petId = 9999; // Идентификатор несуществующего питомца

        given()
                .baseUri(BASE_URL)
                .when()
                .get("/pet/{petId}", petId)
                .then()
                .assertThat()
                .body("message", equalTo("Pet not found"));


    }
    @Test//Позитивный тест-кейс для post запроса хэндлер Pet:
    public void testCreateNewPet() {
        Specs.installSpec(Specs.requestSpec(), Specs.responseSpecOK200());
        // подготовка данных для запроса
        CreateNewPet createNewPet = new CreateNewPet(1804, "Rex","available");
        // отправка post запроса
        given()
                .baseUri(BASE_URL)
                .body(createNewPet)
                .when()
                .post("pet")
                .then()
                .assertThat()
                .body("id", equalTo(1804));// проверка тела ответа
    }
    @Test//Негативный тест-кейс для post запроса хэндлер Pet:
    public void testCreateNewPetWithInvalidData() {
        Specs.installSpec(Specs.requestSpec(), Specs.responseSpecUnique(405));
        // cоздание нового питомца
        CreateNewPet createNewPet = new CreateNewPet(1804, "Sharik","invalid_status");
// в атрибут "status", который состоит из массива [available, pending, sold], поместим невалидное значение
// так как это демонстрационный сервис (Pet Store) он не стабильный и пропускает это значение, хотя не должен
        // отправка post запроса
        given()
                .baseUri(BASE_URL)
                .body(createNewPet)
                .when()
                .post("/pet")
                .then()
                .assertThat()
                .body("message", equalTo("Invalid input"));// проверка тела ответа;
// тест падает, потому что пропустил невалидное значение статус код (200), вместо ожидаемого (405)
// и сообщения "Invalid input"
    }
    @Test//Позитивный тест кейс для delete запроса хэндлер Pet:
    public void deletePetWithValidId() {
//        Specs.installSpec(Specs.requestSpec(), Specs.responseSpecOK200());
        // Создаем новое животное
        CreateNewPet createNewPet = new CreateNewPet(23, "doggie","pending");
        given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(createNewPet)
                .when()
                .post("/pet")
                .then().log().all()
                .statusCode(200)
                .body("id", equalTo(23));// проверка тела ответа;

        // Удаляем животное с указанным id
        given()
                .baseUri(BASE_URL)
                .pathParam("petId", 23)
                .when()
                .delete("/pet/{petId}")
                .then().log().all()
                .statusCode(200)
                .body("message", equalTo("23"));// проверка тела ответа;;

        // Проверяем, что животное удалено
        given()
                .baseUri(BASE_URL)
                .pathParam("petId", 23)
                .when()
                .get("/pet/{petId}")
                .then().log().all()
                .statusCode(404)
                .body("message", equalTo("Pet not found"));// проверка тела ответа;
    }
    @Test//Негативный тест кейс для delete запроса хэндлер Pet:
    public void deleteNonexistentPet() {
        Specs.installSpec(Specs.requestSpec(),Specs.responseSpecUnique(404));
        // Удаляем животное с несуществующим id
        given()
                .baseUri(BASE_URL)
                .pathParam("petId", 999)
                .when()
                .delete("/pet/{petId}")
                .then()
                .assertThat();
    }
}
