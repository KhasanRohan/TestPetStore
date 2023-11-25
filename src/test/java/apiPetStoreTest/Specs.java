package apiPetStoreTest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specs {
    public static RequestSpecification requestSpec(){
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }
    public static ResponseSpecification responseSpecOK200(){
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .log(LogDetail.ALL)
                .build();
    }
    public static ResponseSpecification responseError400(){
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .log(LogDetail.ALL)
                .build();
    }
    public static ResponseSpecification responseSpecUnique(int status){
        return new ResponseSpecBuilder()
                .expectStatusCode(status)
                .log(LogDetail.ALL)
                .build();
    }
    public static void installSpec(RequestSpecification request, ResponseSpecification response){
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }
}
