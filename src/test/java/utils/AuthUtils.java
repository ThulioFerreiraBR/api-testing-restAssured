package utils;

import clients.AuthClient;
import clients.UserClient;
import io.restassured.response.Response;

import java.util.Map;

public class AuthUtils {

    private static final UserClient userClient = new UserClient();
    private static final AuthClient authClient = new AuthClient();

    public static String getValidToken() {

        Response usersResponse = userClient.getUsers();

        String username = usersResponse.path("users[0].username");
        String password = usersResponse.path("users[0].password");

        Map<String, Object> body = Map.of(
                "username", username,
                "password", password
        );

        return authClient
                .login(body)
                .then()
                .extract()
                .path("accessToken");
    }
}