package data;

import lombok.Value;

@Value
public class DataHelper {


    private String cardNumber;
    private String month;
    private String year;
    private String name;
    private String CVC;

    public static DataHelper getApprovedCardInfo() {
        return new DataHelper("4444444444444441", "03", "25", "IVAN IVANOV", "111");
    }

    public static DataHelper getDeclinedCardInfo() {
        return new DataHelper("4444444444444442", "03", "25", "IVAN IVANOV", "111");
    }

}
