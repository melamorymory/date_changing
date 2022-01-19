package ru.netology;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;
import ru.netology.data.RegistrationInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.DataGenerator.generateDate;

public class DateChangingTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");

    @Test
    public void shouldChangeDate() {
        String newDate = generateDate(10);

        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue(info.getCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(info.getDate());
        $x("//*[@name='name']").setValue(info.getName());
        $x("//*[@name='phone']").setValue(info.getPhone());
        $(".checkbox__box").click();
        $$(".button").find(exactText("Запланировать")).click();
        $("[data-test-id='success-notification']").shouldHave(text("Встреча успешно запланирована на " + info.getDate()), Duration.ofSeconds(15)).shouldBe(visible);
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(newDate);
        $$(".button").find(exactText("Запланировать")).click();
        $$(".button__text").find(exactText("Перепланировать")).click();
        $("[data-test-id='success-notification']").shouldHave(text("Встреча успешно запланирована на " + newDate), Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    public void testToFail() {
        String newDate = generateDate(10);

        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue(info.getCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(info.getDate());
        $x("//*[@name='name']").setValue(info.getName());
        $x("//*[@name='phone']").setValue(info.getPhone());
        $(".checkbox__box").click();
        $$(".button").find(exactText("Запланировать")).click();
        $("[data-test-id='success-notification']").shouldHave(text("Встреча успешно запланирована на " + info.getDate()), Duration.ofSeconds(15)).shouldBe(visible);
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(newDate);
        $$(".button").find(exactText("Запланировать")).click();
        $$(".button__text").find(exactText("Перепланировать")).click();
        $("[data-test-id='success-notification']").shouldHave(text("тест падает для красивого отчёта " + newDate), Duration.ofSeconds(15)).shouldBe(visible);
    }
}
