package ru.yandex.practicum.stellarburgers.api;

import io.restassured.response.Response;
import ru.yandex.practicum.stellarburgers.config.Config;

import static io.restassured.RestAssured.given;

public class UserApi {
    
    public static Response createUser(String email, String password, String name) {
        return given()
                .header("Content-type", "application/json")
                .body(String.format("{\"email\":\"%s\",\"password\":\"%s\",\"name\":\"%s\"}", email, password, name))
                .when()
                .post(Config.API_URL + "/auth/register");
    }
    
    public static Response deleteUser(String accessToken) {
        return given()
                .header("Authorization", accessToken != null && !accessToken.startsWith("Bearer ") ? "Bearer " + accessToken : accessToken)
                .when()
                .delete(Config.API_URL + "/auth/user");
    }
    
    public static String loginUser(String email, String password) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password))
                .when()
                .post(Config.API_URL + "/auth/login");
        
        if (response.getStatusCode() == 200) {
            String token = response.getBody().jsonPath().getString("accessToken");
            if (token != null && !token.startsWith("Bearer ")) {
                return "Bearer " + token;
            }
            return token;
        }
        return null;
    }
}
