package tests;

import io.restassured.http.ContentType;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.*;
import pojos.QualITPojo;

import java.util.List;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FinalTests {

    private final static String URL = "http://localhost:8080";

    @Setter
    @Getter
    private static String sessionId;

    //метод для создания нового овоща/фрукта
    void addFood(String name, String type, boolean exotic) {

        given()
                .contentType(ContentType.JSON)
                .cookie("JSESSIONID", getSessionId())
                .body("{\n" +
                        "  \"name\": \"" + name + "\",\n" +
                        "  \"type\": \"" + type + "\",\n" +
                        "  \"exotic\": " + exotic + "\n" +
                        "}")
                .when()
                .post(URL + "/api/food")
                .then();

        System.out.println("*** В базу был добавлен " + name + " ***");
        System.out.println();
        System.out.println("****************************************************************");
        System.out.println();
    }

    //метод для сброса данных
    void dataReset() {

        given()
                .contentType(ContentType.JSON)
                .cookie("JSESSIONID", getSessionId())
                .when()
                .post(URL + "/api/data/reset")
                .then();

        System.out.println("*** Произведен сброс данных ***");
        System.out.println();
        System.out.println("****************************************************************");
        System.out.println();
    }

    //начало выполнения тестов
    @Test
    @Order(1)
    void getFoodList() {

        System.out.println("=== Тест №1: проверяем начальный список продуктов ===");
        System.out.println();

        io.restassured.response.Response response = given()
                .when()
                .get(URL + "/api/food")
                .then()
                .log().status()
                .log().body()
                .extract()
                .response();

        //сохраняю body в лист
        List<QualITPojo> list1 = response.jsonPath().getList(".", QualITPojo.class);

        //сохраняю куки после отправки первого запроса, он будет использоваться во всех последующих запросах
        setSessionId(response.cookie("JSESSIONID"));

        //выполняю проверки
        Assertions.assertAll(
                () -> Assertions.assertEquals("Апельсин", list1.get(0).getName()),
                () -> Assertions.assertEquals("Капуста", list1.get(1).getName()),
                () -> Assertions.assertEquals("Помидор", list1.get(2).getName()),
                () -> Assertions.assertEquals("Яблоко", list1.get(3).getName())
        );
        System.out.println("=== Тест №1 пройден ===");
        System.out.println();
        System.out.println("===========================================================================");
        System.out.println();
    }

    @Test
    @Order(2)
    void getJustAddedVegetable() {

        addFood("Огурец","VEGETABLE",false);
        System.out.println("=== Тест №2: Проверим, что новый овощ действительно был добавлен ===");
        System.out.println();

        List<QualITPojo> list2 = given()
                .cookie("JSESSIONID", getSessionId())
                .when()
                .get(URL + "/api/food")
                .then()
                .log().status()
                .log().body()
                .extract()
                .jsonPath().getList("", QualITPojo.class);
        Assertions.assertAll(
                () -> Assertions.assertNotNull(list2.get(4)),
                () -> Assertions.assertEquals("Огурец", list2.get(4).getName()),
                () -> Assertions.assertEquals("VEGETABLE", list2.get(4).getType())

        );
        System.out.println("===Тест №2 пройден===");
        System.out.println();
        System.out.println("===========================================================================");
        System.out.println();
    }

    @Test
    @Order(3)
    void getJustAddedFruit() {

        addFood("Виноград","FRUIT",false);
        System.out.println("=== Тест №3: Проверим, что новый фрукт действительно был добавлен ===");
        System.out.println();

        List<QualITPojo> list3 = given()
                .cookie("JSESSIONID", getSessionId())
                .when()
                .get(URL + "/api/food")
                .then()
                .log().status()
                .log().body()
                .extract()
                .jsonPath().getList("", QualITPojo.class);
        Assertions.assertAll(
                () -> Assertions.assertNotNull(list3.get(5)),
                () -> Assertions.assertEquals("Виноград", list3.get(5).getName()),
                () -> Assertions.assertEquals("FRUIT", list3.get(5).getType())
        );
        System.out.println("===Тест №3 пройден===");
        System.out.println();
        System.out.println("===========================================================================");
        System.out.println();
    }

    @Test
    @Order(4)
    void getFoodListAfterDataReset() {

        dataReset();
        System.out.println("=== Тест №4: проверяем список продуктов после сброса данных ===");
        System.out.println();

        List<QualITPojo> list4 = given()
                .cookie("JSESSIONID", getSessionId())
                .when()
                .get("/api/food")
                .then()
                .log().status()
                .log().body()
                .extract()
                .jsonPath().getList("", QualITPojo.class);
        Assertions.assertAll(
                () -> Assertions.assertEquals("Апельсин", list4.get(0).getName()),
                () -> Assertions.assertEquals("Капуста", list4.get(1).getName()),
                () -> Assertions.assertEquals("Помидор", list4.get(2).getName()),
                () -> Assertions.assertEquals("Яблоко", list4.get(3).getName())
        );
        System.out.println("=== Тест №4 пройден ===");
        System.out.println();
        System.out.println("===========================================================================");
        System.out.println();
    }
}