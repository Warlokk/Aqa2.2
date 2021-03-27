package ru.netology;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.Keys.*;


public class Delivery {

    private LocalDate plusDays(int n) {
        LocalDate date = LocalDate.now();
        date = date.plusDays(n);

        return date;
    }

    @Test
    void shouldSendForm() {
        open("http://localhost:9999");
        $$("[type=text]").first().setValue("Самара");
        $("[placeholder='Дата встречи']").sendKeys(CONTROL + "a", DELETE);
        String newDate = plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(newDate);
        $("[name=phone]").setValue("+79999999999");
        $("[data-test-id=name] [type=text]").setValue("Иванов Иван");
        $(".checkbox__box").click();
        $("div.form-field>[type=button]").submit();
        $(withText("Успешно")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldUseDropDownCity() {
        open("http://localhost:9999");
        $$("[type=text]").first().sendKeys("а", "с");
//        $(withText("Астрахань")).click();
        $$(".menu-item").find(exactText("Астрахань")).click();
        $("[placeholder='Дата встречи']").sendKeys(CONTROL + "a", DELETE);
        String newDate = plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(newDate);
        $("[name=phone]").setValue("+79999999999");
        $("[data-test-id=name] [type=text]").setValue("Иванов Иван");
        $(".checkbox__box").click();
        $("div.form-field>[type=button]").submit();
        $(withText("Успешно")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldUseCalendar() {
        open("http://localhost:9999");
        $$("[type=text]").first().sendKeys("а", "с");
//        $(withText("Астрахань")).click();
        $$(".menu-item").find(exactText("Астрахань")).click();
        $(".input__icon [type=button]").click();
        LocalDate today = LocalDate.now();
        LocalDate date = plusDays(7);
        String day = Integer.toString(date.getDayOfMonth());
        if ((date.getMonth()) != today.getMonth()) {
            $("[data-step='1']").click();
            $$(".calendar__day").find(exactText(day)).click();
        } else {
            $$(".calendar__day").find(exactText(day)).click();
        }
        String actual = $("[placeholder='Дата встречи']").getValue();
        String expected = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        assertEquals(expected,actual);
        $("[name=phone]").setValue("+79999999999");
        $("[data-test-id=name] [type=text]").setValue("Иванов Иван");
        $(".checkbox__box").click();
        $("div.form-field>[type=button]").submit();
        $(withText("Успешно")).shouldBe(visible, Duration.ofSeconds(15));
    }

}
