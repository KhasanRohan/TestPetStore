package apiPetStoreTest;

import Pojo.AddNewOrder;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import static org.hamcrest.core.Is.is;

public class StoreServiceTest {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    @Test//Позитивный тест кейс для GET запроса хэндлера store:
    public void getStoreOrder() {
        Specs.installSpec(Specs.requestSpec(), Specs.responseSpecOK200());
        int orderId = 6;
        given()
                .baseUri(BASE_URL)
                .when()
                .get("/store/order/{orderId}", orderId)
                .then()
                .assertThat()
                .body("status", equalTo("placed")); // Предположим, что в данном случае ожидаемый результат - 111 доступных животных в магазине
    }
    @Test//Негативный тест кейс для GET запроса хэндлера store:
    public void getNonexistentStoreOrder() {
        Specs.installSpec(Specs.requestSpec(), Specs.responseSpecUnique(404));
        int orderId = 1;
        given()
                .baseUri(BASE_URL)
                .when()
                .get("/store/order/{orderId}", orderId) // Предполагается, что orderId = 10 не существует
                .then().log().all()
                .assertThat()
                .body("message", equalTo("Order not found"));
    }
    @Test//Позитивный тест кейс для POST запроса хэндлера store:
    public void addNewOrderToStore() {
        Specs.installSpec(Specs.requestSpec(), Specs.responseSpecOK200());

        AddNewOrder addNewOrder = new AddNewOrder(1, 1, "placed", true);
        given()
                .baseUri(BASE_URL)
                .body(addNewOrder)
                .when()
                .post("/store/order")
                .then()
                .assertThat()
                .body("status", equalTo("placed"));
    }
    @Test//Негативный тест кейс для POST запроса хэндлера store:
    public void addInvalidOrderToStore() {
        Specs.installSpec(Specs.requestSpec(), Specs.responseError400());

// создание нового заказа, в атрибут "status" который состоит из массива [placed, approved, delivered]
// поместим невалидное значение
        AddNewOrder addNewOrder = new AddNewOrder(1, 1, "invalid_status", false);
        given()
                .baseUri(BASE_URL)
                .body(addNewOrder)
                .when()
                .post("/store/order")
                .then()
                .body("status", equalTo("Invalid Order"));// проверка тела ответа
        //тест падает, потому что пропустил невалидное значение статус код (200), вместо ожидаемого (400)
        // и сообщения "Invalid Order"
    }
    @Test//Позитивный тест кейс для DELETE запроса хэндлера store:
    public void deleteOrderFromStore() {
        Specs.installSpec(Specs.requestSpec(), Specs.responseSpecOK200());
        given()
                .baseUri(BASE_URL)
                .pathParam("orderId", 12)
                .when()
                .delete("/store/order/{orderId}")
                .then()
                .body("message", equalTo("12"));

    }
    @Test//Негативный тест кейс для DELETE запроса хэндлера store:
    public void deleteNonexistentOrderFromStore() {
        Specs.installSpec(Specs.requestSpec(), Specs.responseSpecUnique(404));
        given()
                .baseUri(BASE_URL)
                .pathParam("orderId", 999)
                .when()
                .delete("/store/order/{orderId}")
                .then()
                .body("message", equalTo("Order Not Found"));

    }
}
