package Specifications;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

public class ResponseSpecifications {

    public static ResponseSpecification defaultSpec() {
        ResponseSpecBuilder responseBuilder = new ResponseSpecBuilder();
        responseBuilder.expectHeader("Content-Type", "application/json; charset=utf-8");
        //responseBuilder.expectHeader("Access-Control-Allow-Origin", "https://api-coffee-testing.herokuapp.com/");

        return responseBuilder.build();
    }

    public static ResponseSpecification htmlSpec() {
        ResponseSpecBuilder responseBuilder = new ResponseSpecBuilder();
        responseBuilder.expectHeader("Content-Type", "text/html; charset=utf-8");

        return responseBuilder.build();
    }
}
