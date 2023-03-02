package data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CardData {
    private final String cardNumber;
    private final String month;
    private final String year;
    private final String name;
    private final String CVC;
}
