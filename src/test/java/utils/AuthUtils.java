package utils;

import clients.AuthClient;
import clients.UserClient;
import io.restassured.response.Response;

public class AuthUtils {

    private static final UserClient userClient = new UserClient();
    private static final AuthClient authClient = new AuthClient();

    public static String getValidToken() {

        Response usersResponse = userClient.getUsers();

        String username = usersResponse.path("users[0].username");
        String password = usersResponse.path("users[0].password");

        return authClient
            .login(username, password)
            .then()
            .extract()
            .path("accessToken");
    }
}