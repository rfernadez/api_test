package Specifications;

import helpers.RequestHelpers;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestSpecifications {

    public static RequestSpecification generateToken(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

        String token = RequestHelpers.getUserToken();

        requestSpecBuilder.addHeader("Authorization", "Bearer " + token);
        return requestSpecBuilder.build();
    };

    public static RequestSpecification generateFakeToken(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.addHeader("Authorization", "Beasadrer wrongtoken");
        return requestSpecBuilder.build();
    };

    public static RequestSpecification useBasicAuthentication(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.addHeader("Authorization", "Basic " + "dGVzdHVzZXI6dGVzdHBhc3M=");
        return requestSpecBuilder.build();
    };
}
