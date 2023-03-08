package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.BuyOnCredit;
import page.IntroPage;

import static com.codeborne.selenide.Selenide.open;

public class BuyOnCreditTests {

    private IntroPage introPage;
    private BuyOnCredit buyOnCredit;


    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");
        introPage = new IntroPage();
        buyOnCredit = introPage.clickOnCredit();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    //2 Приобретение тура в кредит, введение корректных данных.
    // Появляется выплывающее окно с сообщением "Успешно! Операция одобрена банком"
    // Информация о том, успешно ли совершен платеж и каким образом, сохраняется в базе данных
    @Test
    public void positiveTestCard() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterAll(cardInfo);
        buyOnCredit.successNotificationCheck();
        Assertions.assertEquals("APPROVED", SQLHelper.getStatusCredit());
    }

    //33 Ввод данных ранее отклоненной карты номер.
    // Появляется выплывающее окно с сообщением "Ошибка! Карта отклонена банком"
    // Информация о том, успешно ли совершен платеж и каким образом, сохраняется в базе данных
    @Test
    public void declinedCardTest() {
        var cardInfo = DataHelper.getDeclinedCardInfo();
        buyOnCredit.enterAll(cardInfo);
        buyOnCredit.mistakeNotificationCheck();
        Assertions.assertEquals("DECLINED", SQLHelper.getStatusCredit());
    }

    //34 Оставить все поля пустыми, нажать кнопку “Продолжить”
    //Появляются сообщения о том, что все поля обязательны для заполнения
    @Test
    public void allWindowsEmptyTest() {
        buyOnCredit.click();
        buyOnCredit.cardNumberWrongFormatCheck("Неверный формат");
        buyOnCredit.monthWrongFormatCheck("Неверный формат");
        buyOnCredit.yearWrongFormatCheck("Неверный формат");
        buyOnCredit.nameWrongFormatCheck("Поле обязательно для заполнения");
        buyOnCredit.cvcWrongFormatCheck("Неверный формат");
    }

    //35 Заполнить все поля корректными данными кроме поля “Номер карты”, его оставить пустым. Нажать кнопку “Продолжить”
    // Появляется сообщение о неверном формате
    @Test
    public void cardNumberWindowEmptyTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.cardNumberWrongFormatCheck("Неверный формат");
    }

    //36 Заполнить все поля корректными данными кроме поля “Месяц”, его оставить пустым. Нажать кнопку “Продолжить”
    // Появляется сообщение о неверном формате

    @Test
    public void monthWindowEmptyTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.monthWrongFormatCheck("Неверный формат");
    }

    //37 Заполнить все поля корректными данными кроме поля “Год”, его оставить пустым. Нажать кнопку “Продолжить”
    // Появляется сообщение о неверном формате
    @Test
    public void yearWindowEmptyTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.yearWrongFormatCheck("Неверный формат");
    }

    //38 Заполнить все поля корректными данными кроме поля “Владелец”, его оставить пустым. Нажать кнопку “Продолжить”
    // Появляется сообщение о том, что поле обязательно для заполнения
    @Test
    public void nameWindowEmptyTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.nameWrongFormatCheck("Поле обязательно для заполнения");
    }

    //39 Заполнить все поля корректными данными кроме поля “CVC/CVV”, его оставить пустым. Нажать кнопку “Продолжить”
    // Появляется сообщение о неверном формате
    @Test
    public void cvcWindowEmptyTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.click();
        buyOnCredit.cvcWrongFormatCheck("Неверный формат");
    }

    //40	Заполнить поле “Номер карты” - ввести 15 цифр “4444 4444 4444 444”
    // Появляется сообщение “Неверный формат”
    @Test
    public void cardNumber15NumbersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber("4444 4444 4444 444");
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.cardNumberWrongFormatCheck("Неверный формат");
    }

    //41 Заполнить поле “Номер карты” - ввести 17 цифр “4444 4444 4444 44411”
    //Поле заполняется первыми 16ю цифрами, 17я обрезается
    @Test
    public void cardNumber17NumbersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber("4444 4444 4444 44411");
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.successNotificationCheck();
    }

    //42 Заполнить поле “Номер карты” - ввести буквы “IVANOV”
    // Появляется сообщение “Неверный формат”
    @Test
    public void cardNumberLettersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber("IVANOV");
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.cardNumberWrongFormatCheck("Неверный формат");
    }

    //43 Заполнить поле “Номер карты” - ввести 16 нулей “0000000000000000”
    // Появляется сообщение “Неверный формат”
    @Test
    public void cardNumberZerosTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber("0000 0000 0000 0000");
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.cardNumberWrongFormatCheck("Неверный формат");
    }

    //44 Заполнить поле “Номер карты” - ввести спецсимволы “4444 4444 4444 44!!”
    // Появляется сообщение "Неверный формат"
    @Test
    public void cardNumberSpecialSymbolsTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber("4444 4444 4444 44!!");
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.cardNumberWrongFormatCheck("Неверный формат");
    }

    //45 Заполнить поле “Месяц” - ввести “0”	Появляется сообщение “Неверный формат”
    @Test
    public void monthOneZeroTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth("0");
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.monthWrongFormatCheck("Неверный формат");
    }

    //46 Заполнить поле “Месяц” - ввести “00”	Появляется сообщение “Неверный формат”
    @Test
    public void monthTwoZerosTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth("00");
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.monthWrongFormatCheck("Неверный формат");
    }

    //47 Заполнить поле “Месяц” - ввести “13”	Появляется сообщение “Неверно указан срок действия карты”
    @Test
    public void month13Test() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth("13");
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.monthWrongFormatCheck("Неверно указан срок действия карты");
    }

    //48 Заполнить поле “Месяц” - ввести буквы “MM”	Поле “Месяц” не заполняется
    @Test
    public void monthLettersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth("MM");
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.monthWrongFormatCheck("Неверный формат");
    }

    //49 Заполнить поле “Месяц” - ввести спецсимволы “!!”	Поле “Месяц” не заполняется
    @Test
    public void monthSymbolsTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth("!!");
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.monthWrongFormatCheck("Неверный формат");
    }

    //50 Заполнить поле “Год” - ввести уже прошедший год. Появляется сообщение “Истек срок действия карты”
    @Test
    public void lastYearTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(DataHelper.getLatYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.yearWrongFormatCheck("Истёк срок действия карты");
    }

    //51 Заполнить поле “Год” - ввести “0”	Появляется сообщение “Неверный формат”
    @Test
    public void yearOneZeroTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear("0");
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.yearWrongFormatCheck("Неверный формат");
    }

    //52 Заполнить поле “Год”, ввести буквы “MM”. Поле “Год” не заполняется
    @Test
    public void yearLettersOneZeroTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear("MM");
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.yearWrongFormatCheck("Неверный формат");
    }

    //53 Заполнить поле “Год” - ввести спецсимволы “!!”	Поле “Год” не заполняется
    @Test
    public void yearSymbolsTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear("!!");
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.yearWrongFormatCheck("Неверный формат");
    }

    //54 Заполнить поле “Владелец” - ввести 30 символов “IVANIVANOVIVANIVANOVIVANIVANOV”
    // Появляется сообщение “Неверный формат”
    @Test
    public void name30LettersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName("IVANIVANOVIVANIVANOVIVANIVANOV");
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.nameWrongFormatCheck("Неверный формат");
    }

    //55 Заполнить поле “Владелец” - ввести 1 символ “V”	Появляется сообщение “Неверный формат”
    @Test
    public void name1LetterTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName("V");
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.nameWrongFormatCheck("Неверный формат");
    }

    //56 Заполнить поле “Владелец” - ввести имя на кириллице “ИВАН ИВАНОВ”	Поле “Владелец” не заполняется
    @Test
    public void nameInCyrillicTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName("ИВАН ИВАНОВ");
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.nameWrongFormatCheck("Поле обязательно для заполнения");
    }

    //57 Заполнить поле “Владелец” - ввести спецсимволы “!!”	Поле “Владелец” не заполняется
    @Test
    public void nameSymbolsTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName("!!");
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.nameWrongFormatCheck("Поле обязательно для заполнения");
    }

    //58 Заполнить поле “Владелец” - ввести цифры "11". Поле “Владелец” не заполняется
    @Test
    public void nameNumbersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName("11");
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.nameWrongFormatCheck("Поле обязательно для заполнения");
    }

    //59 Заполнить поле “CVC/CVV” - ввести “000”	Появляется сообщение “Неверный формат”
    @Test
    public void cvcThreeZerosTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC("000");
        buyOnCredit.click();
        buyOnCredit.cvcWrongFormatCheck("Неверный формат");
    }

    //60 Заполнить поле “CVC/CVV” - ввести 4 цифры “2354”
    // Поле заполняется первыми 3мя цифрами, 4я обрезается
    @Test
    public void cvcFourNumbersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC("2354");
        buyOnCredit.click();
        buyOnCredit.successNotificationCheck();
    }

    //61 Заполнить поле “CVC/CVV” - ввести буквы “AAA”	Поле “CVC/CVV” не заполняется
    @Test
    public void cvcLettersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC("AAA");
        buyOnCredit.click();
        buyOnCredit.cvcWrongFormatCheck("Неверный формат");
    }

    //62 Заполнить поле “CVC/CVV” - спецсимволы “!!!”	Поле “CVC/CVV” не заполняется
    @Test
    public void cvcSymbolsTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC("!!!");
        buyOnCredit.click();
        buyOnCredit.cvcWrongFormatCheck("Неверный формат");
    }
}
