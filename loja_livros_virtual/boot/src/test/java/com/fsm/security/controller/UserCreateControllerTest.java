package com.fsm.security.controller;

import com.fsm.security.dto.UserDTO;
import io.micronaut.http.HttpStatus;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

@MicronautTest

public class UserCreateControllerTest {

    @Test
    public void testCreateUserEndpoint(RequestSpecification spec){

        // Create credentials
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials("admin", "admin");

        // Login request
        Response loginResponse = spec
                .contentType("application/json")
                .body(creds)
                .when()
                .post("/login");

        // Status check
        assertEquals(HttpStatus.OK.getCode(), loginResponse.getStatusCode());

        // Extract token
        String accessToken = loginResponse.jsonPath().getString("access_token");

        String userName = "testUser" + System.currentTimeMillis();

        String password = "testPassword" + System.currentTimeMillis();

        UserDTO roleUser = new UserDTO(userName, password, Set.of(("ROLE_USER")));

        spec.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .body(roleUser)
        .when()
                .post("user")
        .then().statusCode(201);

    }
}
