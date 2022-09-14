package philips.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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

    @Test
    public void naoDeveSalvarUsuarioSemNome(){

        given()
            .log().all()
            .contentType("application/json")
            .body("{\"age\": 50 }")
        .when()
            .post("https://restapi.wcaquino.me/users")
        .then()
            .log().all()
            .statusCode(400)
            .body("id", is(Matchers.nullValue()))
            .body("error", is("Name é um atributo obrigatório"))
        ;
    }

    @Test
    public void deveSalvarUsuarioXml(){

        given()
            .log().all()
            .contentType(ContentType.XML)
            .body("<user><name>Joaozinho</name><age>43</age></user>")
        .when()
            .post("https://restapi.wcaquino.me/usersXML")
        .then()
            .log().all()
            .statusCode(201)
            .body("user.id", is(notNullValue()))
            .body("user.name", is("Joaozinho"))
            .body("user.age", is("43"))
        ;
    }

    @Test
    public void deveAlterarUsuarioSemNome(){

        given()
            .log().all()
            .contentType("application/json")
            .body("{ \"name\": \"Zezao\", \"age\": 99 }")
        .when()
            .put("https://restapi.wcaquino.me/users/1")
        .then()
            .log().all()
            .statusCode(200)
            .body("id", is(1))
            .body("name", is("Zezao"))
            .body("age", is(99))
            .body("salary", is(1234.5678f))
        ;
    }
}
