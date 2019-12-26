package sanekels.bendergamblerbot;

import java.util.ArrayList;
import java.util.List;

public class DeckOfCards {

    List<TheCard> thisDeck = new ArrayList<>();
    int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};
    String[] names = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    String[] suits = {"\u2665", "\u2666", "\u2663", "\u2660"};

    DeckOfCards() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                assert thisDeck != null;
                thisDeck.add(new TheCard(values[j], names[j], suits[i]));
            }
        }
    }

    public TheCard takeOneCard() {
        int randIndex = (int) (Math.random() * thisDeck.size());
        TheCard current = thisDeck.get(randIndex);
        thisDeck.remove(randIndex);
        return current;
    }

    public void refreshDeck() {
        thisDeck.clear();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                assert thisDeck != null;
                thisDeck.add(new TheCard(values[j], names[j], suits[i]));
            }
        }
    }
}
