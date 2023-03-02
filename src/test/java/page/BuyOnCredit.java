package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.CardData;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BuyOnCredit {
    private SelenideElement cardNumber = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement month = $("[placeholder='08']");
    private SelenideElement year = $("[placeholder='22']");
    private SelenideElement name = $(byText("Владелец")).parent().$("input");
    private SelenideElement cvc = $("[placeholder='999']");
    private SelenideElement button = $$("button").find(Condition.exactText("Продолжить"));
    private SelenideElement notificationSuccess = $(byText("Успешно")).parent().$("div");
    private SelenideElement notificationMistake = $(byText("Ошибка")).parent().$("div");
    private SelenideElement cardNumberWrongFormat = $(byText("Номер карты")).parent().$(".input__sub");
    private SelenideElement monthWrongFormat = $(byText("Месяц")).parent().$(".input__sub");
    private SelenideElement yearWrongFormat = $(byText("Год")).parent().$(".input__sub");
    //private SelenideElement nameShouldBeFilled = $(byText("Поле обязательно для заполнения"));
    private SelenideElement nameShouldBeFilled = $(byText("Владелец")).parent().$(".input__sub");
    private SelenideElement cvcWrongFormat = $(byText("CVC/CVV")).parent().$(".input__sub");

    public void enterAll(CardData cardInfo) {
        cardNumber.val(cardInfo.getCardNumber());
        month.val(cardInfo.getMonth());
        year.val(cardInfo.getYear());
        name.val(cardInfo.getName());
        cvc.val(cardInfo.getCVC());
        button.click();
    }

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
    }

    public void successNotificationCheck() {
        notificationSuccess.shouldBe((Condition.visible), Duration.ofSeconds(15));
    }

    public void mistakeNotificationCheck() {
        notificationMistake.shouldBe((Condition.visible), Duration.ofSeconds(15));
    }

    public void cardNumberWrongFormatCheck() {
        cardNumberWrongFormat.shouldBe(Condition.visible).shouldHave(Condition.exactText("Неверный формат"));
    }

    public void monthWrongFormatCheck() {
        monthWrongFormat.shouldBe(Condition.visible).shouldHave(Condition.exactText("Неверный формат"));
    }

    public void monthWrongDateFormatCheck() {
        monthWrongFormat.shouldBe(Condition.visible).shouldHave(Condition.exactText("Неверно указан срок действия карты"));
    }

    public void yearWrongFormatCheck() {
        yearWrongFormat.shouldBe(Condition.visible).shouldHave(Condition.exactText("Неверный формат"));
    }

    public void yearExpiredCardCheck() {
        yearWrongFormat.shouldBe(Condition.visible).shouldHave(Condition.exactText("Истёк срок действия карты"));
    }

    public void nameShouldBeFilledCheck() {
        nameShouldBeFilled.shouldBe(Condition.visible).shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    public void nameWrongFormatCheck() {
        nameShouldBeFilled.shouldBe(Condition.visible).shouldHave(Condition.exactText("Неверный формат"));
    }

    public void cvcWrongFormatCheck() {
        cvcWrongFormat.shouldBe(Condition.visible).shouldHave(Condition.exactText("Неверный формат"));
    }

}