package sanekels.bendergamblerbot;

import java.util.ArrayList;
import java.util.List;

public class BlackJackGamers {
    private List<TheCard> currentHand = new ArrayList<>();
    private int intValueOfHand = 0;

    List<TheCard> AllCards() {
        return currentHand;
    }

    void addCard(TheCard newCard) {
        currentHand.add(newCard);
        intValueOfHand += newCard.value;
    }

    int intValueOfHand() {
        return intValueOfHand;
    }

    String stringValueOfHand() {
        String valueOfHand = "";
        for (int i = 0; i < currentHand.size(); i++) {
            valueOfHand += currentHand.get(i).suit + " " + currentHand.get(i).name + "; ";
        }
        return valueOfHand + "points: " + this.intValueOfHand();
    }

    void clearCards() {
        currentHand.clear();
        intValueOfHand = 0;
    }
}
