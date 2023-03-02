package test;

import data.DataHelper;
import data.SQLHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.BuyOnCredit;
import page.IntroPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;


public class BuyOnCreditTests {

    private IntroPage introPage;
    private BuyOnCredit buyOnCredit;


    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");
        introPage = new IntroPage();
        buyOnCredit = introPage.clickOnCredit();
    }

    //3 Приобретение тура в кредит, введение корректных данных.
    // Появляется выплывающее окно с сообщением "Успешно! Операция одобрена банком"
    @Test
    public void positiveGUITestCard() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterAll(cardInfo);
        buyOnCredit.successNotificationCheck();
    }

    //4 Приобретение тура в кредит, введение корректных данных
    // Информация о том, успешно ли совершен платеж и каким образом, сохраняется в базе данных
    @Test
    public void positiveAPITestCard() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterAll(cardInfo);
        sleep(15000);
        Assertions.assertEquals("APPROVED", SQLHelper.getStatusCredit());
    }

    //37 При покупке в кредит ввод данных ранее отклоненной карты
    // Информация о том, успешно ли совершен платеж и каким образом, сохраняется в базе данных
    @Test
    public void APITestDeclinedCard() {
        var cardInfo = DataHelper.getDeclinedCardInfo();
        buyOnCredit.enterAll(cardInfo);
        sleep(15000);
        Assertions.assertEquals("DECLINED", SQLHelper.getStatusCredit());
    }

    //36 Ввод данных ранее отклоненной карты номер.
    // Появляется выплывающее окно с сообщением "Ошибка! Карта отклонена банком"
    @Test
    public void GUITestDeclinedCard() {
        var cardInfo = DataHelper.getDeclinedCardInfo();
        buyOnCredit.enterAll(cardInfo);
        buyOnCredit.mistakeNotificationCheck();
    }

    //38 Оставить все поля пустыми, нажать кнопку “Продолжить”
    //Появляются сообщения о том, что все поля обязательны для заполнения
    @Test
    public void AllWindowsEmptyTest() {
        buyOnCredit.click();
        buyOnCredit.cardNumberWrongFormatCheck();
        buyOnCredit.monthWrongFormatCheck();
        buyOnCredit.yearWrongFormatCheck();
        buyOnCredit.nameShouldBeFilledCheck();
        buyOnCredit.cvcWrongFormatCheck();
    }

    //39	Заполнить все поля корректными данными кроме поля “Номер карты”, его оставить пустым. Нажать кнопку “Продолжить”
    // Появляется сообщение о неверном формате
    @Test
    public void CardNumberWindowEmptyTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.cardNumberWrongFormatCheck();
    }

    //40	Заполнить все поля корректными данными кроме поля “Месяц”, его оставить пустым. Нажать кнопку “Продолжить”
    // Появляется сообщение о неверном формате

    @Test
    public void MonthWindowEmptyTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.monthWrongFormatCheck();
    }

    //41	Заполнить все поля корректными данными кроме поля “Год”, его оставить пустым. Нажать кнопку “Продолжить”
    // Появляется сообщение о неверном формате
    @Test
    public void YearWindowEmptyTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.yearWrongFormatCheck();
    }

    //42 Заполнить все поля корректными данными кроме поля “Владелец”, его оставить пустым. Нажать кнопку “Продолжить”
    // Появляется сообщение о том, что поле обязательно для заполнения
    @Test
    public void NameWindowEmptyTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.nameShouldBeFilledCheck();
    }

    //43 Заполнить все поля корректными данными кроме поля “CVC/CVV”, его оставить пустым. Нажать кнопку “Продолжить”
    // Появляется сообщение о неверном формате

    @Test
    public void CVCWindowEmptyTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.click();
        buyOnCredit.cvcWrongFormatCheck();
    }

    //44	Заполнить поле “Номер карты” - ввести 15 цифр “4444 4444 4444 444”
    // Появляется сообщение “Неверный формат”
    @Test
    public void CardNumber15NumbersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber("4444 4444 4444 444");
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.cardNumberWrongFormatCheck();
    }

    //45	Заполнить поле “Номер карты” - ввести 17 цифр “4444 4444 4444 44411”
    //Поле заполняется первыми 16ю цифрами, 17я обрезается
    @Test
    public void CardNumber17NumbersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber("4444 4444 4444 44411");
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.successNotificationCheck();
    }

    //46	Заполнить поле “Номер карты” - ввести буквы “IVANOV”
    // Появляется сообщение “Неверный формат”
    @Test
    public void CardNumberLettersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber("IVANOV");
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.cardNumberWrongFormatCheck();
    }

    //47	Заполнить поле “Номер карты” - ввести 16 нулей “0000000000000000”
    // Появляется сообщение “Неверный формат”
    @Test
    public void CardNumberZerosTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber("0000 0000 0000 0000");
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.cardNumberWrongFormatCheck();
    }

    //48	Заполнить поле “Номер карты” - ввести спецсимволы “4444 4444 4444 44!!”
    // Появляется сообщение "Неверный формат"
    @Test
    public void CardNumberSpecialSymbolsTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber("4444 4444 4444 44!!");
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.cardNumberWrongFormatCheck();
    }

    //49	Заполнить поле “Месяц” - ввести “0”	Появляется сообщение “Неверный формат”
    @Test
    public void MonthOneZeroTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth("0");
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.monthWrongFormatCheck();
    }

    //50	Заполнить поле “Месяц” - ввести “00”	Появляется сообщение “Неверный формат”
    @Test
    public void MonthTwoZerosTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth("00");
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.monthWrongFormatCheck();
    }

    //51	Заполнить поле “Месяц” - ввести “13”	Появляется сообщение “Неверно указан срок действия карты”
    @Test
    public void Month13Test() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth("13");
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.monthWrongDateFormatCheck();
    }

    //52	Заполнить поле “Месяц” - ввести буквы “MM”	Поле “Месяц” не заполняется
    @Test
    public void MonthLettersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth("MM");
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.monthWrongFormatCheck();
    }

    //53	Заполнить поле “Месяц” - ввести спецсимволы “!!”	Поле “Месяц” не заполняется
    @Test
    public void MonthSymbolsTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth("!!");
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.monthWrongFormatCheck();
    }

    //54	Заполнить поле “Год” - ввести уже прошедший год. Появляется сообщение “Истек срок действия карты”
    @Test
    public void LastYearTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(DataHelper.getLatYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.yearExpiredCardCheck();
    }

    //55	Заполнить поле “Год” - ввести “0”	Появляется сообщение “Неверный формат”
    @Test
    public void YearOneZeroTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear("0");
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.yearWrongFormatCheck();
    }

    //56	Заполнить поле “Год”, ввести буквы “MM”. Поле “Год” не заполняется
    @Test
    public void YearLettersOneZeroTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear("MM");
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.yearWrongFormatCheck();
    }

    //57	Заполнить поле “Год” - ввести спецсимволы “!!”	Поле “Год” не заполняется
    @Test
    public void YearSymbolsTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear("!!");
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.yearWrongFormatCheck();
    }

    //58	Заполнить поле “Владелец” - ввести 30 символов “IVANIVANOVIVANIVANOVIVANIVANOV”
    // Появляется сообщение “Неверный формат”
    @Test
    public void Name30LettersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName("IVANIVANOVIVANIVANOVIVANIVANOV");
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.nameWrongFormatCheck();
    }

    //59	Заполнить поле “Владелец” - ввести 1 символ “V”	Появляется сообщение “Неверный формат”
    @Test
    public void Name1LetterTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName("V");
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.nameWrongFormatCheck();
    }

    //60	Заполнить поле “Владелец” - ввести имя на кириллице “ИВАН ИВАНОВ”	Поле “Владелец” не заполняется
    @Test
    public void NameInCyrillicTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName("ИВАН ИВАНОВ");
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.nameShouldBeFilledCheck();
    }

    //61	Заполнить поле “Владелец” - ввести спецсимволы “!!”	Поле “Владелец” не заполняется
    @Test
    public void NameSymbolsTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName("!!");
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.nameShouldBeFilledCheck();
    }

    //62	Заполнить поле “Владелец” - ввести цифры "11". Поле “Владелец” не заполняется
    @Test
    public void NameNumbersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName("11");
        buyOnCredit.enterCVC(cardInfo.getCVC());
        buyOnCredit.click();
        buyOnCredit.nameShouldBeFilledCheck();
    }

    //63	Заполнить поле “CVC/CVV” - ввести “000”	Появляется сообщение “Неверный формат”
    @Test
    public void CVCThreeZerosTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC("000");
        buyOnCredit.click();
        buyOnCredit.cvcWrongFormatCheck();
    }

    //64	Заполнить поле “CVC/CVV” - ввести 4 цифры “2354”
    // Поле заполняется первыми 3мя цифрами, 4я обрезается
    @Test
    public void CVCFourNumbersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC("2354");
        buyOnCredit.click();
        buyOnCredit.successNotificationCheck();
    }

    //65	Заполнить поле “CVC/CVV” - ввести буквы “AAA”	Поле “CVC/CVV” не заполняется
    @Test
    public void CVCLettersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC("AAA");
        buyOnCredit.click();
        buyOnCredit.cvcWrongFormatCheck();
    }

    //66	Заполнить поле “CVC/CVV” - спецсимволы “!!!”	Поле “CVC/CVV” не заполняется
    @Test
    public void CVCSymbolsTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        buyOnCredit.enterCardNumber(cardInfo.getCardNumber());
        buyOnCredit.enterMonth(cardInfo.getMonth());
        buyOnCredit.enterYear(cardInfo.getYear());
        buyOnCredit.enterName(cardInfo.getName());
        buyOnCredit.enterCVC("!!!");
        buyOnCredit.click();
        buyOnCredit.cvcWrongFormatCheck();
    }
}
