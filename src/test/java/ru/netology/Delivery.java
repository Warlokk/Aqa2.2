package ru.netology;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
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
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Встреча успешно забронирована на " + newDate));

    }

    @Test
    void shouldUseDropDownCity() {
        open("http://localhost:9999");
        $$("[type=text]").first().sendKeys("а", "с");
        $(byText("Астрахань")).click();
        $("[placeholder='Дата встречи']").sendKeys(CONTROL + "a", DELETE);
        String newDate = plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(newDate);
        $("[name=phone]").setValue("+79999999999");
        $("[data-test-id=name] [type=text]").setValue("Иванов Иван");
        $(".checkbox__box").click();
        $("div.form-field>[type=button]").submit();
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Встреча успешно забронирована на " + newDate));

    }

    @Test
    void shouldUseCalendar() {
        open("http://localhost:9999");
        $$("[type=text]").first().sendKeys("а", "с");
        $$(".menu-item").find(exactText("Астрахань")).click();
        $(".input__icon [type=button]").click();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateInCalendar = $("[placeholder='Дата встречи']").getValue();
        LocalDate date = LocalDate.parse(dateInCalendar, formatter);
        LocalDate newDate = plusDays(7);
        String day = Integer.toString(newDate.getDayOfMonth());

        if (newDate.getYear() > date.getYear()) {
            $("[data-step='12']").click();
        }

        if (newDate.getMonthValue() > date.getMonthValue()) {
            int monthCount = (newDate.getMonthValue() - date.getMonthValue());
            for (int i = 0; i < monthCount; i++) {
                $("[data-step='1']").click();
            }
        } else if (newDate.getMonthValue() < date.getMonthValue()) {
            int monthCount = (date.getMonthValue() - newDate.getMonthValue());
            for (int i = 0; i < monthCount; i++) {
                $("[data-step='-1']").click();
            }
        }
        $$(".calendar__day").find(exactText(day)).click();
        String expected = newDate.format(formatter);
        $("[name=phone]").setValue("+79999999999");
        $("[data-test-id=name] [type=text]").setValue("Иванов Иван");
        $(".checkbox__box").click();
        $("div.form-field>[type=button]").submit();
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Встреча успешно забронирована на " + expected));

    }

}
