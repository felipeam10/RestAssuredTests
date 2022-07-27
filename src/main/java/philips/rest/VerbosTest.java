package philips.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Test;

public class VerbosTest {

    @Test
    public void deveSalvarUsuario(){

        given()
                .log().all()
                .contentType("application/json")
                .body("{ \"name\": \"Zezim\", \"age\": 50 }")
        .when()
                .post("https://restapi.wcaquino.me/users")
        .then()
                .log().all()
                .statusCode(201)
                .body("id", is(Matchers.notNullValue()))
                .body("name", is("Zezim"))
                .body("age", is(50))
        ;
    }
}
