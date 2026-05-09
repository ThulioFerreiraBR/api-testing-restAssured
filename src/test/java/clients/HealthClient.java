package clients;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class HealthClient {

        public Response getStatus(){
            return given()
                    .when()
                    .get("/test");
        }
}
