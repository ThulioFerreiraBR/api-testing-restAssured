package tests;

import base.BaseTest;
import clients.UserClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class UserTest extends BaseTest {

    // -------------------------------------------------------------------------
    // Constants
    // -------------------------------------------------------------------------

    private static final long RESPONSE_TIME_MS = 3000L;

    // -------------------------------------------------------------------------
    // Clients
    // -------------------------------------------------------------------------

    private final UserClient userClient = new UserClient();

    // -------------------------------------------------------------------------
    // Happy path
    // -------------------------------------------------------------------------

    @Test
    @Tag("smoke")
    @DisplayName("Should return users successfully")
    void shouldReturnUsersSuccessfully() {

        userClient.getUsers()
                .then()
                .statusCode(200)
                .header("Content-Type", containsString("application/json"))
                .time(lessThan(RESPONSE_TIME_MS))
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
