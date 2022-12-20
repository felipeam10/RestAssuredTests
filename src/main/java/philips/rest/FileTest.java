package philips.rest;

import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class FileTest {

    @Test
    public void deveObrigarEnvioDoArquivo(){
        given()
                .log().all()
        .when()
                .post("https://restapi.wcaquino.me/upload")
        .then()
                .log().all()
                .statusCode(404)
                .body("error", Matchers.is("Arquivo não enviado"))
        ;
    }

    @Test
    public void deveFazerUploadDoArquivo(){
        given()
                .log().all()
                .multiPart("arquivo", new File("src/main/resources/users.pdf"))
        .when()
                .post("https://restapi.wcaquino.me/upload")
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("users.pdf"))
        ;
    }

    @Test
    public void naoDeveFazerUploadDeArquivoGrande(){
        given()
                .log().all()
                .multiPart("arquivo", new File("src/main/resources/VID-20221114-WA0144.mp4"))
        .when()
                .post("https://restapi.wcaquino.me/upload")
        .then()
                .log().all()
                .time(lessThan(2000L))
                .statusCode(413)
        ;
    }
}
