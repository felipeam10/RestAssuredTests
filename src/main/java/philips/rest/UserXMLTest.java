package philips.rest;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;

public class UserXMLTest {

    @Test
    public void trabalhandoComXml(){
        given()
        .when()
            .get("https://restapi.wcaquino.me/usersXML/3")
        .then()
            .statusCode(200)
            .rootPath("user")
            .body("name", is("Ana Julia"))
            .body("@id", is("3"))
            .rootPath("user.filhos")
            .body("name.size()", is(2))
            .body("name[0]", is("Zezinho"))
            .body("name[1]", is("Luizinho"))
            .body("name", hasItem("Luizinho"))
            .body("name", hasItems("Luizinho", "Zezinho"))
        ;
    }
}
