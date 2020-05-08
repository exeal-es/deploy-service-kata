package com.exeal.deployservice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeployRegistryAcceptanceTests {

    @LocalServerPort
    private int port;

    @MockBean
    Clock clock;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void deployment_history() {
        givenVersion1WasDeployedAtDateD1WithSuccess();
        givenVersion2WasDeployedAtDateD2WithRollback();
        Response response = whenIFetchDeploymentHistory();
        thenTheDeploymentListContainsTheseDeployments(response);
    }

    private void givenVersion1WasDeployedAtDateD1WithSuccess() {
        when(clock.now()).thenReturn(LocalDateTime.of(2020, 3, 15, 10, 12, 34));
        createDeployment("firstDeployment.json");
    }

    private void givenVersion2WasDeployedAtDateD2WithRollback() {
        when(clock.now()).thenReturn(LocalDateTime.of(2020, 3, 18, 15, 49, 57));
        createDeployment("secondDeployment.json");
    }

    private void createDeployment(String fileName) {
        given()
                .body(readFile(fileName))
                .contentType(ContentType.JSON)
                .post("/deployments")
                .then()
                .statusCode(200);
    }

    private Response whenIFetchDeploymentHistory() {
        return given().get("/deployments");
    }

    private void thenTheDeploymentListContainsTheseDeployments(Response response) {
        response
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("", equalToFixture("deploymentHistory.json"));
    }

    private Matcher<Object> equalToFixture(String fileName) {
        String fileContents = readFile(fileName);
        return equalTo(new JsonPath(fileContents).get(""));
    }

    private String readFile(String fileName) {
        try {
            java.net.URL url = getClass().getClassLoader().getResource(fileName);
            java.nio.file.Path resPath = java.nio.file.Paths.get(url.toURI());
            return new String(java.nio.file.Files.readAllBytes(resPath), StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}