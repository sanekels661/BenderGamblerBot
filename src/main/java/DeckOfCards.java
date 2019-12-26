import java.util.ArrayList;
import java.util.List;

public class DeckOfCards {

    public class PartsOfCard {
        int value;
        String name;
        String suit;

        public PartsOfCard(int value1, String name1, String suit1) {
            name = name1;
            value = value1;
            suit = suit1;
        }
    }

    List<PartsOfCard> thisDeck = new ArrayList<>();
    int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};
    String[] names = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    String[] suits = {"\u2665", "\u2666", "\u2663", "\u2660"};

    DeckOfCards() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                assert thisDeck != null;
                thisDeck.add(new PartsOfCard(values[j], names[j], suits[i]));
            }
        }
    }

    public PartsOfCard TakeOneCard() {
        int randIndex = (int) (Math.random() * thisDeck.size());
        PartsOfCard current = thisDeck.get(randIndex);
        thisDeck.remove(randIndex);
        return current;
    }

}
