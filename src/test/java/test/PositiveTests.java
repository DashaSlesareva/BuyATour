package test;

import data.DataHelper;
import data.SQLHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.BuyOnCredit;
import page.IntroPage;
import page.PayWithCard;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

public class PositiveTests {

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");
    }

    //1. Приобретение тура с использованием дебетовой карты, введение корректных данных
    // Появляется выплывающее окно с сообщением "Успешно! Операция одобрена банком"
    @Test
    public void positiveGUITestCard() {
        var userInfo = DataHelper.getApprovedCardInfo();
        IntroPage introPage = new IntroPage();
        PayWithCard payWithCard = introPage.clickOnBuy();
        payWithCard.enterAll(userInfo);
        payWithCard.successNotificationCheck();
    }

    //2. Приобретение тура с использованием дебетовой карты, введение корректных данных
    // Информация о том, успешно ли совершен платеж и каким образом, сохраняется в базе данных
    @Test
    public void positiveAPITestCard() {
        var userInfo = DataHelper.getApprovedCardInfo();
        IntroPage introPage = new IntroPage();
        PayWithCard payWithCard = introPage.clickOnBuy();
        payWithCard.enterAll(userInfo);
        sleep(10000);
        Assertions.assertEquals("APPROVED", SQLHelper.getStatusCard() );
    }

    //3. Приобретение тура в кредит, введение корректных данных
    // Появляется выплывающее окно с сообщением "Успешно! Операция одобрена банком"
    @Test
    public void positiveGUITestCredit() {
        var userInfo = DataHelper.getApprovedCardInfo();
        IntroPage introPage = new IntroPage();
        BuyOnCredit buyOnCredit = introPage.clickOnCredit();
        buyOnCredit.enterAll(userInfo);
        buyOnCredit.successNotificationCheck();
    }

    //4. Приобретение тура в кредит, введение корректных данных
    // Информация о том, успешно ли совершен платеж и каким образом, сохраняется в базе данных
    @Test
    public void positiveAPITestCredit() {
        var userInfo = DataHelper.getApprovedCardInfo();
        IntroPage introPage = new IntroPage();
        BuyOnCredit buyOnCredit = introPage.clickOnCredit();
        buyOnCredit.enterAll(userInfo);
        sleep(10000);
        Assertions.assertEquals("APPROVED", SQLHelper.getStatusCredit());
    }

    //6. При покупке по карте ввод данных ранее отклоненной карты
    // Информация о том, успешно ли совершен платеж и каким образом, сохраняется в базе данных
    @Test
    public void negativeAPITestCard() {
        var userInfo = DataHelper.getDeclinedCardInfo();
        IntroPage introPage = new IntroPage();
        PayWithCard payWithCard = introPage.clickOnBuy();
        payWithCard.enterAll(userInfo);
        sleep(10000);
        Assertions.assertEquals("DECLINED",SQLHelper.getStatusCard());
    }

    //37. При покупке в кредит ввод данных ранее отклоненной карты
    // Информация о том, успешно ли совершен платеж и каким образом, сохраняется в базе данных
    @Test
    public void negativeAPITestCredit() {
        var userInfo = DataHelper.getDeclinedCardInfo();
        IntroPage introPage = new IntroPage();
        BuyOnCredit buyOnCredit = introPage.clickOnCredit();
        buyOnCredit.enterAll(userInfo);
        sleep(10000);
        Assertions.assertEquals("DECLINED",SQLHelper.getStatusCredit());
    }



}
