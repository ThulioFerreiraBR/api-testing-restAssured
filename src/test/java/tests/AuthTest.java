package tests;

import base.BaseTest;
import clients.AuthClient;
import clients.UserClient;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class AuthTest extends BaseTest {

    private final UserClient userClient = new UserClient();
    private final AuthClient authClient = new AuthClient();

    @Test
    @DisplayName("Should login successfully")
    void shouldLoginSuccessfully(){

        Response usersResponse = userClient.getUsers();

        String username = usersResponse.path("users[0].username");
        String password = usersResponse.path("users[0].password");

        Response loginResponse = authClient.login(username, password);

        loginResponse
            .then()
            .statusCode(200)
            .header("Content-Type", containsString("application/json"))
            .time(lessThan(3000L))
            .body("accessToken", not(emptyOrNullString()))
            .body("refreshToken", not(emptyOrNullString()))
            .body("username", equalTo(username))
            .body("email", not(emptyOrNullString()))
            .body("id", greaterThan(0));
    }

}
