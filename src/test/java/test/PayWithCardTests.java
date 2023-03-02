package test;

import data.DataHelper;
import data.SQLHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.IntroPage;
import page.PayWithCard;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

public class PayWithCardTests {
    private IntroPage introPage;
    private PayWithCard payWithCard;


    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");
        introPage = new IntroPage();
        payWithCard = introPage.clickOnBuy();
    }

    //1 Приобретение тура с использованием дебетовой карты, введение корректных данных.
    // Появляется выплывающее окно с сообщением "Успешно! Операция одобрена банком"
    @Test
    public void positiveGUITestCard() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterAll(cardInfo);
        payWithCard.successNotificationCheck();
    }

    //2 Приобретение тура с использованием дебетовой карты, введение корректных данных
    // Информация о том, успешно ли совершен платеж и каким образом, сохраняется в базе данных
    @Test
    public void positiveAPITestCard() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterAll(cardInfo);
        sleep(15000);
        Assertions.assertEquals("APPROVED", SQLHelper.getStatusCard());
    }

    //6 При покупке по карте ввод данных ранее отклоненной карты
    // Информация о том, успешно ли совершен платеж и каким образом, сохраняется в базе данных
    @Test
    public void APITestDeclinedCard() {
        var cardInfo = DataHelper.getDeclinedCardInfo();
        payWithCard.enterAll(cardInfo);
        sleep(15000);
        Assertions.assertEquals("DECLINED", SQLHelper.getStatusCard());
    }

    //5 Ввод данных ранее отклоненной карты номер.
    // Появляется выплывающее окно с сообщением "Ошибка! Карта отклонена банком"
    @Test
    public void GUITestDeclinedCard() {
        var cardInfo = DataHelper.getDeclinedCardInfo();
        payWithCard.enterAll(cardInfo);
        payWithCard.mistakeNotificationCheck();
    }

    //7 Оставить все поля пустыми, нажать кнопку “Продолжить”
    //Появляются сообщения о том, что все поля обязательны для заполнения
    @Test
    public void AllWindowsEmptyTest() {
        payWithCard.click();
        payWithCard.cardNumberWrongFormatCheck();
        payWithCard.monthWrongFormatCheck();
        payWithCard.yearWrongFormatCheck();
        payWithCard.nameShouldBeFilledCheck();
        payWithCard.cvcWrongFormatCheck();
    }

    //8	Заполнить все поля корректными данными кроме поля “Номер карты”, его оставить пустым. Нажать кнопку “Продолжить”
    // Появляется сообщение о неверном формате
    @Test
    public void CardNumberWindowEmptyTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.cardNumberWrongFormatCheck();
    }

    //9	Заполнить все поля корректными данными кроме поля “Месяц”, его оставить пустым. Нажать кнопку “Продолжить”
    // Появляется сообщение о неверном формате

    @Test
    public void MonthWindowEmptyTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.monthWrongFormatCheck();
    }

    //10	Заполнить все поля корректными данными кроме поля “Год”, его оставить пустым. Нажать кнопку “Продолжить”
    // Появляется сообщение о неверном формате
    @Test
    public void YearWindowEmptyTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.yearWrongFormatCheck();
    }

    //11	Заполнить все поля корректными данными кроме поля “Владелец”, его оставить пустым. Нажать кнопку “Продолжить”
    // Появляется сообщение о том, что поле обязательно для заполнения
    @Test
    public void NameWindowEmptyTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.nameShouldBeFilledCheck();
    }

    //Заполнить все поля корректными данными кроме поля “CVC/CVV”, его оставить пустым. Нажать кнопку “Продолжить”
    // Появляется сообщение о неверном формате

    @Test
    public void CVCWindowEmptyTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.click();
        payWithCard.cvcWrongFormatCheck();
    }

    //13	Заполнить поле “Номер карты” - ввести 15 цифр “4444 4444 4444 444”
    // Появляется сообщение “Неверный формат”
    @Test
    public void CardNumber15NumbersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber("4444 4444 4444 444");
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.cardNumberWrongFormatCheck();
    }

    //14	Заполнить поле “Номер карты” - ввести 17 цифр “4444 4444 4444 44411”
    //Поле заполняется первыми 16ю цифрами, 17я обрезается
    @Test
    public void CardNumber17NumbersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber("4444 4444 4444 44411");
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.successNotificationCheck();
    }

    //15	Заполнить поле “Номер карты” - ввести буквы “IVANOV”
    // Появляется сообщение “Неверный формат”
    @Test
    public void CardNumberLettersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber("IVANOV");
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.cardNumberWrongFormatCheck();
    }

    //16	Заполнить поле “Номер карты” - ввести 16 нулей “0000000000000000”
    // Появляется сообщение “Неверный формат”
    @Test
    public void CardNumberZerosTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber("0000 0000 0000 0000");
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.cardNumberWrongFormatCheck();
    }

    //17	Заполнить поле “Номер карты” - ввести спецсимволы “4444 4444 4444 44!!”
    // Появляется сообщение "Неверный формат"
    @Test
    public void CardNumberSpecialSymbolsTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber("4444 4444 4444 44!!");
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.cardNumberWrongFormatCheck();
    }

    //18	Заполнить поле “Месяц” - ввести “0”	Появляется сообщение “Неверный формат”
    @Test
    public void MonthOneZeroTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth("0");
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.monthWrongFormatCheck();
    }

    //19	Заполнить поле “Месяц” - ввести “00”	Появляется сообщение “Неверный формат”
    @Test
    public void MonthTwoZerosTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth("00");
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.monthWrongFormatCheck();
    }

    //20	Заполнить поле “Месяц” - ввести “13”	Появляется сообщение “Неверно указан срок действия карты”
    @Test
    public void Month13Test() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth("13");
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.monthWrongDateFormatCheck();
    }

    //21	Заполнить поле “Месяц” - ввести буквы “MM”	Поле “Месяц” не заполняется
    @Test
    public void MonthLettersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth("MM");
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.monthWrongFormatCheck();
    }

    //22	Заполнить поле “Месяц” - ввести спецсимволы “!!”	Поле “Месяц” не заполняется
    @Test
    public void MonthSymbolsTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth("!!");
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.monthWrongFormatCheck();
    }

    //23	Заполнить поле “Год” - ввести уже прошедший год. Появляется сообщение “Истек срок действия карты”
    @Test
    public void LastYearTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(DataHelper.getLatYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.yearExpiredCardCheck();
    }

    //24	Заполнить поле “Год” - ввести “0”	Появляется сообщение “Неверный формат”
    @Test
    public void YearOneZeroTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear("0");
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.yearWrongFormatCheck();
    }

    //25	Заполнить поле “Год”, ввести буквы “MM”. Поле “Год” не заполняется
    @Test
    public void YearLettersOneZeroTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear("MM");
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.yearWrongFormatCheck();
    }

    //26	Заполнить поле “Год” - ввести спецсимволы “!!”	Поле “Год” не заполняется
    @Test
    public void YearSymbolsTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear("!!");
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.yearWrongFormatCheck();
    }

    //27	Заполнить поле “Владелец” - ввести 30 символов “IVANIVANOVIVANIVANOVIVANIVANOV”
    // Появляется сообщение “Неверный формат”
    @Test
    public void Name30LettersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName("IVANIVANOVIVANIVANOVIVANIVANOV");
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.nameWrongFormatCheck();
    }

    //28	Заполнить поле “Владелец” - ввести 1 символ “V”	Появляется сообщение “Неверный формат”
    @Test
    public void Name1LetterTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName("V");
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.nameWrongFormatCheck();
    }

    //29	Заполнить поле “Владелец” - ввести имя на кириллице “ИВАН ИВАНОВ”	Поле “Владелец” не заполняется
    @Test
    public void NameInCyrillicTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName("ИВАН ИВАНОВ");
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.nameShouldBeFilledCheck();
    }

    //30	Заполнить поле “Владелец” - ввести спецсимволы “!!”	Поле “Владелец” не заполняется
    @Test
    public void NameSymbolsTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName("!!");
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.nameShouldBeFilledCheck();
    }

    //31	Заполнить поле “Владелец” - ввести цифры "11". Поле “Владелец” не заполняется
    @Test
    public void NameNumbersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName("11");
        payWithCard.enterCVC(cardInfo.getCVC());
        payWithCard.click();
        payWithCard.nameShouldBeFilledCheck();
    }

    //32	Заполнить поле “CVC/CVV” - ввести “000”	Появляется сообщение “Неверный формат”
    @Test
    public void CVCThreeZerosTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC("000");
        payWithCard.click();
        payWithCard.cvcWrongFormatCheck();
    }

    //33	Заполнить поле “CVC/CVV” - ввести 4 цифры “2354”
    // Поле заполняется первыми 3мя цифрами, 4я обрезается
    @Test
    public void CVCFourNumbersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC("2354");
        payWithCard.click();
        payWithCard.successNotificationCheck();
    }

    //34	Заполнить поле “CVC/CVV” - ввести буквы “AAA”	Поле “CVC/CVV” не заполняется
    @Test
    public void CVCLettersTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC("AAA");
        payWithCard.click();
        payWithCard.cvcWrongFormatCheck();
    }

    //35	Заполнить поле “CVC/CVV” - спецсимволы “!!!”	Поле “CVC/CVV” не заполняется
    @Test
    public void CVCSymbolsTest() {
        var cardInfo = DataHelper.getApprovedCardInfo();
        payWithCard.enterCardNumber(cardInfo.getCardNumber());
        payWithCard.enterMonth(cardInfo.getMonth());
        payWithCard.enterYear(cardInfo.getYear());
        payWithCard.enterName(cardInfo.getName());
        payWithCard.enterCVC("!!!");
        payWithCard.click();
        payWithCard.cvcWrongFormatCheck();
    }
}
