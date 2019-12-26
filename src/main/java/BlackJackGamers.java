import java.util.ArrayList;
import java.util.List;

public class BlackJackGamers {
    private List<DeckOfCards.PartsOfCard> currentHand = new ArrayList<>();

    List<DeckOfCards.PartsOfCard> AllCards() {
        return currentHand;
    }

    void addCard(DeckOfCards.PartsOfCard newCard) {
        currentHand.add(newCard);
    }

    int intValueOfHand() {
        int valueOfHand = 0;
        for (int i = 0; i < currentHand.size(); i++) {
            valueOfHand += currentHand.get(i).value;
        }
        return valueOfHand;
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
    }
}
