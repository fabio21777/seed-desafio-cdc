package com.fsm;


import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.restassured.specification.RequestSpecification;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.UUID;

@Singleton
public class UtilsTest {

    @Inject
    RequestSpecification spec;

    private String token;

    public String getToken() {
        if (token == null) {
            this.token = spec
                    .given()
                    .contentType("application/json")
                    .body(new UsernamePasswordCredentials("admin", "admin"))
                    .when()
                    .post("/login")
                    .then()
                    .statusCode(200)
                    .extract()
                    .path("access_token");
        }
        return token;
    }


    public static String uuid() {
        return UUID.randomUUID().toString();
    }
}