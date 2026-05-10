package tests;

import base.BaseTest;
import clients.UserClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class UserTest extends BaseTest {

    private final UserClient userClient = new UserClient();

    @Test
    @DisplayName("Should return users successfully")
    void shouldReturnUsersSuccessfully(){

        userClient.getUsers()
            .then()
            .statusCode(200)
            .header("Content-Type", containsString("application/json"))
            .time(lessThan(3000L))
            .body("users", notNullValue())
            .body("users.size()", greaterThan(0))
            .body("users.username", everyItem(not(emptyOrNullString())))
            .body("users.password", everyItem(not(emptyOrNullString())))
            .body("users.id", everyItem(greaterThan(0)))
            .body("users.firstName", everyItem(not(emptyOrNullString())))
            .body("users.email", everyItem(not(emptyOrNullString())))
            .body("users.role", everyItem(anyOf(
                equalTo("admin"),
                equalTo("moderator"),
                equalTo("user")
            )))
            .body("total", greaterThan(0))
            .body("limit", greaterThan(0));
    }

}
