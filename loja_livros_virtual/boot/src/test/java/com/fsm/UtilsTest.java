package com.fsm;


import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.restassured.specification.RequestSpecification;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Random;
import java.util.UUID;

@Singleton
public class UtilsTest {

    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int SIGLA_LENGTH = 2;
    private static final Random RANDOM = new Random();

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

    public static String gerarSiglaAleatoria() {
        StringBuilder sb = new StringBuilder(SIGLA_LENGTH);
        for (int i = 0; i < SIGLA_LENGTH; i++) {
            int index = RANDOM.nextInt(LETTERS.length());
            sb.append(LETTERS.charAt(index));
        }
        return sb.toString();
    }
}