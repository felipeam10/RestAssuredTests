package philips.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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
    public void deveAlterarUsuario(){

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

    @Test
    public void devoCustomizarURL(){

        given()
            .log().all()
            .contentType("application/json")
            .body("{ \"name\": \"Zezao\", \"age\": 99 }")
        .when()
            .put("https://restapi.wcaquino.me/{entidade}/{userId}","users", "1")
        .then()
            .log().all()
            .statusCode(200)
            .body("id", is(1))
            .body("name", is("Zezao"))
            .body("age", is(99))
            .body("salary", is(1234.5678f))
        ;
    }

    @Test
    public void devoCustomizarURLParte2(){

        given()
            .log().all()
            .contentType("application/json")
            .body("{ \"name\": \"Zezao\", \"age\": 99 }")
            .pathParam("entidade", "users")
            .pathParam("userId", 1)
        .when()
            .put("https://restapi.wcaquino.me/{entidade}/{userId}")
        .then()
            .log().all()
            .statusCode(200)
            .body("id", is(1))
            .body("name", is("Zezao"))
            .body("age", is(99))
            .body("salary", is(1234.5678f))
        ;
    }

    @Test
    public void deveRemoverUsuario(){

        given()
            .log().all()
        .when()
            .delete("https://restapi.wcaquino.me/users/1")
        .then()
            .log().all()
            .statusCode(204)
        ;
    }

    @Test
    public void naoDeveRemoverUsuarioInexistente(){

        given()
            .log().all()
        .when()
            .delete("https://restapi.wcaquino.me/users/1000")
        .then()
            .log().all()
            .statusCode(400)
            .body("error", is("Registro inexistente"))
        ;
    }

    @Test
    public void deveSalvarObjetoUsandoMap(){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", "UsuarioMap");
        params.put("age", 25);

        given()
            .log().all()
            .contentType("application/json")
            .body(params)
        .when()
            .post("https://restapi.wcaquino.me/users")
        .then()
            .log().all()
            .statusCode(201)
            .body("id", is(Matchers.notNullValue()))
            .body("name", is("UsuarioMap"))
            .body("age", is(25))
        ;
    }

    @Test
    public void deveSalvarUsuarioUsandoObjeto(){
        User user = new User("UsuarioViaObjeto", 35);

        given()
            .log().all()
            .contentType("application/json")
            .body(user)
        .when()
            .post("https://restapi.wcaquino.me/users")
        .then()
            .log().all()
            .statusCode(201)
            .body("id", is(Matchers.notNullValue()))
            .body("name", is("UsuarioViaObjeto"))
            .body("age", is(35))
        ;
    }

    @Test
    public void deveDeserializarObjetoAoSalvarUsuario(){
        User user = new User("UsuarioDeserializado", 39);

        User usuarioInserido = given()
            .log().all()
            .contentType("application/json")
            .body(user)
        .when()
            .post("https://restapi.wcaquino.me/users")
        .then()
            .log().all()
            .statusCode(201)
            .extract().body().as(User.class);
        ;

        System.out.println(usuarioInserido);
        Assert.assertThat(usuarioInserido.getId(), notNullValue());
        Assert.assertEquals("UsuarioDeserializado", usuarioInserido.getName());
        Assert.assertThat(usuarioInserido.getAge(), is(39));
    }

    @Test
    public void deveSalvarUsuarioViaXMLUsandoObjeto(){
        User user = new User("UsuarioSerializadoXML", 40);

        given()
            .log().all()
            .contentType(ContentType.XML)
            .body(user)
        .when()
            .post("https://restapi.wcaquino.me/usersXML")
        .then()
            .log().all()
            .statusCode(201)
            .body("user.id", is(notNullValue()))
            .body("user.name", is("UsuarioSerializadoXML"))
            .body("user.age", is("40"))
        ;
    }

}
