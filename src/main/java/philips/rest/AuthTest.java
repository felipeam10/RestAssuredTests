package philips.rest;

import io.restassured.http.ContentType;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXParseException;

import java.io.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthTest {

    @Test
    public void deveAcessarSWAPI(){
        given()
                .log().all()
        .when()
                .get("https://swapi.dev/api/people/1/")
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Luke Skywalker"))
        ;

    }

    // eab70ae38085cf75b3761861628b68df

    @Test
    public void deveObterClima(){
        given()
                .log().all()
                .queryParam("q", "Uberlandia,BR")
                .queryParam("appid", "eab70ae38085cf75b3761861628b68df")
                .queryParam("units", "metric")
        .when()
                .get("https://api.openweathermap.org/data/2.5/weather")
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Uberlândia"))
                .body("coord.lon", is(-48.2772f))
                .body("main.temp", is(19.56f))
        ;

    }

    @Test
    public void naoDeveAcessarSemSenha(){
        given()
                .log().all()
        .when()
                .get("https://restapi.wcaquino.me/basicauth")
        .then()
                .log().all()
                .statusCode(401)
        ;

    }

    @Test
    public void deveEfetuarAutenticacaoBasica(){
        given()
                .log().all()
        .when()
                .get("https://admin:senha@restapi.wcaquino.me/basicauth")
        .then()
                .log().all()
                .statusCode(200)
                .body("status", is("logado"))
        ;

    }

    @Test
    public void deveEfetuarAutenticacaoBasica2(){
        given()
                .log().all()
                .auth().basic("admin", "senha")
        .when()
                .get("https://restapi.wcaquino.me/basicauth")
        .then()
                .log().all()
                .statusCode(200)
                .body("status", is("logado"))
        ;

    }

    @Test
    public void deveEfetuarAutenticacaoBasicaChallenge(){
        given()
                .log().all()
                .auth().preemptive().basic("admin", "senha")
        .when()
                .get("https://restapi.wcaquino.me/basicauth2")
        .then()
                .log().all()
                .statusCode(200)
                .body("status", is("logado"))
        ;

    }
}
