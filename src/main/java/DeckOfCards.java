import java.util.ArrayList;

public class DeckOfCards {

    public  class PartsOfCard {
        int value;
        String name;
        String suit;

        public PartsOfCard(int value1, String name1, String suit1) {
            name = name1;
            value = value1;
            suit = suit1;
        }
    }

    ArrayList<PartsOfCard> thisDeck = new ArrayList<>();

    int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 10};
    String[] names = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    String[] suits = {"Hrt", "Dmn", "Trf", "Spd"};
    //{"♥", "♦", "♣", "♠"};
    DeckOfCards() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                assert thisDeck != null;
                thisDeck.add(new PartsOfCard(values[i], names[j], suits[i]));
            }
        }
    }
    public PartsOfCard TakeOneCard(){
        int randIndex = (int) (Math.random() * thisDeck.size());
        PartsOfCard current = thisDeck.get(randIndex);
        thisDeck.remove(randIndex);
        return current;
    }

}
