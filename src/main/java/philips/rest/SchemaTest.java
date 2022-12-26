package philips.rest;

import io.restassured.http.ContentType;
import io.restassured.matcher.RestAssuredMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXParseException;

import java.io.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SchemaTest {

    @Test
    public void deveValidarSchemaXML() {
        given()
                .log().all()
        .when()
                .get("https://restapi.wcaquino.me/usersXML")
        .then()
                .log()
                .all()
                .body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"))
        ;
    }

    @Test(expected = SAXParseException.class)
    public void naoDeveValidarSchemaXMLInvalido() {
        given()
                .log().all()
        .when()
                .get("https://restapi.wcaquino.me/invalidUsersXML")
        .then()
                .log().all()
                .body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"))
        ;
    }
}
