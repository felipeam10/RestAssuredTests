package philips.rest;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.*;

public class HelloWordTest {

    @Test
    public void testHelloWord() {
        Response response = request(Method.GET, "https://restapi.wcaquino.me/ola");
        Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
        Assert.assertTrue(response.statusCode() == 200);
        Assert.assertTrue("O status code deveria ser 200", response.statusCode() == 200);
        Assert.assertEquals(200, response.statusCode());

        ValidatableResponse validacao = response.then();
        validacao.statusCode(200);
    }

    @Test
    public void modoFluenteWithRestAssured() {
        get("https://restapi.wcaquino.me/ola").then().statusCode(200);

//      modo fluente
        given()
        .when()
            .get("https://restapi.wcaquino.me/ola")
        .then()
            .statusCode(200);
    }
}
