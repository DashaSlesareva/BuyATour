package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PayWithCard {

    private SelenideElement cardNumber = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement month = $("[placeholder='08']");
    private SelenideElement year = $("[placeholder='22']");
    private SelenideElement name = $("div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__box > input");
    private SelenideElement cvc = $("[placeholder='999']");
    private SelenideElement button = $$("button").find(Condition.exactText("Продолжить"));
    private SelenideElement notificationSuccess = $("#root > div > div.notification.notification_visible.notification_status_ok.notification_has-closer.notification_stick-to_right.notification_theme_alfa-on-white");


    public void enterCardNumber(String cardInfo) {
        cardNumber.val(cardInfo);
    }

    public void enterMonth(String monthInfo) {
        month.val(monthInfo);
    }

    public void enterYear(String yearInfo) {
        year.val(yearInfo);
    }

    public void enterName(String nameInfo) {
        name.val(nameInfo);
    }

    public void enterCVC(String cvcInfo) {
        cvc.val(cvcInfo);
    }

    public void click() {
        button.click();
        notificationSuccess.shouldHave(Condition.text("Успешно"), Duration.ofSeconds(10))
                .shouldBe(Condition.visible);
    }

    public void successNotificationCheck() {
        notificationSuccess.shouldHave(Condition.text("Успешно"), Duration.ofSeconds(10))
                .shouldBe(Condition.visible);
    }

    public void enterAll(DataHelper cardInfo) {
        cardNumber.val(cardInfo.getCardNumber());
        month.val(cardInfo.getMonth());
        year.val(cardInfo.getYear());
        name.val(cardInfo.getName());
        cvc.val(cardInfo.getCVC());
        button.click();
    }
}