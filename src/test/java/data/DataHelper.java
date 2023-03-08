package data;


import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;


public class DataHelper {


    public static Random random = new Random();

    public static CardData getApprovedCardInfo() {
        String cardNumber = approvedCard();
        String month = generateMonth();
        String year = generateYear();
        String name = generateName();
        String CVC = generateCVC();
        CardData card = new CardData(cardNumber, month, year, name, CVC);
        return card;
    }

    public static CardData getDeclinedCardInfo() {
        String cardNumber = declinedCard();
        String month = generateMonth();
        String year = generateYear();
        String name = generateName();
        String CVC = generateCVC();
        CardData card = new CardData(cardNumber, month, year, name, CVC);
        return card;
    }


    public static String approvedCard() {
        return "4444444444444441";
    }

    public static String declinedCard() {
        return "4444444444444442";
    }

    public static String generateMonth() {
        List<String> monthsList = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        int index = random.nextInt(monthsList.size());
        return monthsList.get(index);
    }

    public static String generateYear() {
        int year = LocalDate.now().plusYears(random.nextInt(3) + 1).getYear();
        return String.valueOf(year).substring(2);
    }

    public static String getLatYear() {
        int year = LocalDate.now().minusYears(1).getYear();
        return String.valueOf(year).substring(2);
    }

    public static String generateName() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String generateCVC() {
        Faker faker = new Faker();
        return String.valueOf(faker.number().numberBetween(100, 999));
    }

}
