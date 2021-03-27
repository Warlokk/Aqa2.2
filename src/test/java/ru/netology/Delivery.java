package ru.netology;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.*;


public class Delivery {

    @Test
    void shouldSendForm() {
        open("http://localhost:9999");
        $$("[type=text]").first().setValue("Самара");
        $("[placeholder='Дата встречи']").sendKeys(CONTROL + "a", DELETE);
        $("[placeholder='Дата встречи']").setValue(plusDays(4));
        $("[name=phone]").setValue("+79999999999");
        $("[data-test-id=name] [type=text]").setValue("Иванов Иван");
        $(".checkbox__box").click();
        $("div.form-field>[type=button]").submit();
        $(withText("Успешно")).shouldBe(visible, Duration.ofSeconds(15));
    }


    private String plusDays(int n) {
        LocalDate date = LocalDate.now();
        date = date.plusDays(n);
        String newDate = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
        System.out.println("date" + newDate);
        return newDate;
    }


}
