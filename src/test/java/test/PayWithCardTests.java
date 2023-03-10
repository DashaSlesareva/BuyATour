package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.IntroPage;
import page.PayWithCard;

import static com.codeborne.selenide.Selenide.open;

public class PayWithCardTests {
    private IntroPage introPage;
    private PayWithCard payWithCard;


    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");
        introPage = new IntroPage();
        payWithCard = introPage.clickOnBuy();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    //1 Приобретение тура с использованием дебетовой карты, введение корректных данных.
    // Появляется выплывающее окно с сообщением "Успешно! Операция одобрена банком".
    // Информация о том, успешно ли совершен платеж и каким образом, сохраняется в базе данных
    @Test
    public void positiveTestCard() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterAll(cardInfo);
        payWithCard.successNotificationCheck();
        Assertions.assertEquals("APPROVED", SQLHelper.getStatusCard());
    }

    //3 Ввод данных ранее отклоненной карты номер.
    // Появляется выплывающее окно с сообщением "Ошибка! Карта отклонена банком".
    // Информация о том, успешно ли совершен платеж и каким образом, сохраняется в базе данных
    @Test
    public void declinedCardTest() {
        var cardInfo = DataHelper.getDeclinedCardInfo();
        payWithCard.enterAll(cardInfo);
        payWithCard.mistakeNotificationCheck();
        Assertions.assertEquals("DECLINED", SQLHelper.getStatusCard());
    }

    //4 Оставить все поля пустыми, нажать кнопку “Продолжить”
    //Появляются сообщения о том, что все поля обязательны для заполнения
    @Test
    public void allWindowsEmptyTest() {
        payWithCard.click();
        payWithCard.cardNumberWrongFormatCheck("Неверный формат");
        payWithCard.monthWrongFormatCheck("Неверный формат");
        payWithCard.yearWrongFormatCheck("Неверный формат");
        payWithCard.nameWrongFormatCheck("Поле обязательно для заполнения");
        payWithCard.cvcWrongFormatCheck("Неверный формат");
    }

    //5	Заполнить все поля корректными данными кроме поля “Номер карты”, его оставить пустым. Нажать кнопку “Продолжить”
    // Появляется сообщение о неверном формате
    @Test
    public void cardNumberWindowEmptyTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.cardNumberWrongFormatCheck("Неверный формат");
    }

    //6	Заполнить все поля корректными данными кроме поля “Месяц”, его оставить пустым. Нажать кнопку “Продолжить”
    // Появляется сообщение о неверном формате

    @Test
    public void monthWindowEmptyTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.monthWrongFormatCheck("Неверный формат");
    }

    //7	Заполнить все поля корректными данными кроме поля “Год”, его оставить пустым. Нажать кнопку “Продолжить”
    // Появляется сообщение о неверном формате
    @Test
    public void yearWindowEmptyTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.yearWrongFormatCheck("Неверный формат");
    }

    //8	Заполнить все поля корректными данными кроме поля “Владелец”, его оставить пустым. Нажать кнопку “Продолжить”
    // Появляется сообщение о том, что поле обязательно для заполнения
    @Test
    public void nameWindowEmptyTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.nameWrongFormatCheck("Поле обязательно для заполнения");
    }

    //9 Заполнить все поля корректными данными кроме поля “CVC/CVV”, его оставить пустым. Нажать кнопку “Продолжить”
    // Появляется сообщение о неверном формате
    @Test
    public void cvcWindowEmptyTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.click();
        payWithCard.cvcWrongFormatCheck("Неверный формат");
    }

    //10 Заполнить поле “Номер карты” - ввести 15 цифр “4444 4444 4444 444”
    // Появляется сообщение “Неверный формат”
    @Test
    public void cardNumber15NumbersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber("4444 4444 4444 444");
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.cardNumberWrongFormatCheck("Неверный формат");
    }

    //11 Заполнить поле “Номер карты” - ввести 17 цифр “4444 4444 4444 44411”
    //Поле заполняется первыми 16ю цифрами, 17я обрезается
    @Test
    public void cardNumber17NumbersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber("4444 4444 4444 44411");
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.successNotificationCheck();
    }

    //12 Заполнить поле “Номер карты” - ввести буквы “IVANOV”
    // Появляется сообщение “Неверный формат”
    @Test
    public void cardNumberLettersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber("IVANOV");
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.cardNumberWrongFormatCheck("Неверный формат");
    }

    //13 Заполнить поле “Номер карты” - ввести 16 нулей “0000000000000000”
    // Появляется сообщение “Неверный формат”
    @Test
    public void cardNumberZerosTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber("0000 0000 0000 0000");
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.cardNumberWrongFormatCheck("Неверный формат");
    }

    //14 Заполнить поле “Номер карты” - ввести спецсимволы “4444 4444 4444 44!!”
    // Появляется сообщение "Неверный формат"
    @Test
    public void cardNumberSpecialSymbolsTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber("4444 4444 4444 44!!");
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.cardNumberWrongFormatCheck("Неверный формат");
    }

    //15 Заполнить поле “Месяц” - ввести “0”	Появляется сообщение “Неверный формат”
    @Test
    public void monthOneZeroTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth("0");
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.monthWrongFormatCheck("Неверный формат");
    }

    //16 Заполнить поле “Месяц” - ввести “00”	Появляется сообщение “Неверный формат”
    @Test
    public void monthTwoZerosTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth("00");
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.monthWrongFormatCheck("Неверный формат");
    }

    //17 Заполнить поле “Месяц” - ввести “13”	Появляется сообщение “Неверно указан срок действия карты”
    @Test
    public void month13Test() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth("13");
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.monthWrongFormatCheck("Неверно указан срок действия карты");
    }

    //18 Заполнить поле “Месяц” - ввести буквы “MM”	Поле “Месяц” не заполняется
    @Test
    public void monthLettersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth("MM");
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.monthWrongFormatCheck("Неверный формат");
    }

    //19 Заполнить поле “Месяц” - ввести спецсимволы “!!”	Поле “Месяц” не заполняется
    @Test
    public void monthSymbolsTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth("!!");
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.monthWrongFormatCheck("Неверный формат");
    }

    //20 Заполнить поле “Год” - ввести уже прошедший год. Появляется сообщение “Истек срок действия карты”
    @Test
    public void lastYearTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(DataHelper.getLatYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.yearWrongFormatCheck("Истёк срок действия карты");
    }

    //21 Заполнить поле “Год” - ввести “0”	Появляется сообщение “Неверный формат”
    @Test
    public void yearOneZeroTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear("0");
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.yearWrongFormatCheck("Неверный формат");
    }

    //22 Заполнить поле “Год”, ввести буквы “MM”. Поле “Год” не заполняется
    @Test
    public void yearLettersOneZeroTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear("MM");
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.yearWrongFormatCheck("Неверный формат");
    }

    //23 Заполнить поле “Год” - ввести спецсимволы “!!”	Поле “Год” не заполняется
    @Test
    public void yearSymbolsTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear("!!");
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.yearWrongFormatCheck("Неверный формат");
    }

    //24 Заполнить поле “Владелец” - ввести 30 символов “IVANIVANOVIVANIVANOVIVANIVANOV”
    // Появляется сообщение “Неверный формат”
    @Test
    public void name30LettersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName("IVANIVANOVIVANIVANOVIVANIVANOV");
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.nameWrongFormatCheck("Неверный формат");
    }

    //25 Заполнить поле “Владелец” - ввести 1 символ “V”	Появляется сообщение “Неверный формат”
    @Test
    public void name1LetterTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName("V");
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.nameWrongFormatCheck("Неверный формат");
    }

    //26 Заполнить поле “Владелец” - ввести имя на кириллице “ИВАН ИВАНОВ”	Поле “Владелец” не заполняется
    @Test
    public void nameInCyrillicTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName("ИВАН ИВАНОВ");
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.nameWrongFormatCheck("Поле обязательно для заполнения");
    }

    //27 Заполнить поле “Владелец” - ввести спецсимволы “!!”	Поле “Владелец” не заполняется
    @Test
    public void nameSymbolsTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName("!!");
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.nameWrongFormatCheck("Поле обязательно для заполнения");
    }

    //28 Заполнить поле “Владелец” - ввести цифры "11". Поле “Владелец” не заполняется
    @Test
    public void nameNumbersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName("11");
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.nameWrongFormatCheck("Поле обязательно для заполнения");
    }

    //29 Заполнить поле “CVC/CVV” - ввести “000”	Появляется сообщение “Неверный формат”
    @Test
    public void cvcThreeZerosTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC("000");
        payWithCard.click();
        payWithCard.cvcWrongFormatCheck("Неверный формат");
    }

    //30 Заполнить поле “CVC/CVV” - ввести 4 цифры “2354”
    // Поле заполняется первыми 3мя цифрами, 4я обрезается
    @Test
    public void cvcFourNumbersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC("2354");
        payWithCard.click();
        payWithCard.successNotificationCheck();
    }

    //31 Заполнить поле “CVC/CVV” - ввести буквы “AAA”	Поле “CVC/CVV” не заполняется
    @Test
    public void cvcLettersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC("AAA");
        payWithCard.click();
        payWithCard.cvcWrongFormatCheck("Неверный формат");
    }

    //32 Заполнить поле “CVC/CVV” - спецсимволы “!!!”	Поле “CVC/CVV” не заполняется
    @Test
    public void cvcSymbolsTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC("!!!");
        payWithCard.click();
        payWithCard.cvcWrongFormatCheck("Неверный формат");
    }
}
