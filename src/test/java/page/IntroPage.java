package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$$;

public class IntroPage {

    private SelenideElement buy = $$("button").find(Condition.exactText("Купить"));
    private SelenideElement credit = $$("button").find(Condition.exactText("Купить в кредит"));


    public PayWithCard clickOnBuy() {
        buy.click();
        return new PayWithCard();
    }

    public BuyOnCredit clickOnCredit(){
        credit.click();
        return new BuyOnCredit();
    }




}
