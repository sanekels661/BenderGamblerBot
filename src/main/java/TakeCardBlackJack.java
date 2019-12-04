import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class TakeCardBlackJack extends BotCommand{

    private static final String commandIdentifier = "take";
    private static final String description = "take card";
    public TakeCardBlackJack() {
        super(commandIdentifier, description);
    }

    private DeckOfCards currentDeck = new DeckOfCards();
    private ArrayList<DeckOfCards.PartsOfCard> yourHand = new ArrayList<>();
    private ArrayList<DeckOfCards.PartsOfCard> dilerHand = new ArrayList<>();
    private int valueOfPlayerHand = 0;
    private int valueOfDilerHand = 0;


//    DeckOfCards.PartsOfCard firstCard = currentDeck.TakeOneCa rd();
//
//        yourHand.add(firstCard);
//    valueOfHand += firstCard.value;

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        if (currentDeck.thisDeck.size() < 26){
            currentDeck = new DeckOfCards();
        }
        DeckOfCards.PartsOfCard currentCardPlayer = currentDeck.TakeOneCard();
        DeckOfCards.PartsOfCard currentCardDiler = currentDeck.TakeOneCard();
        dilerHand.add(currentCardDiler);
        valueOfDilerHand += currentCardDiler.value;
        yourHand.add(currentCardPlayer);
        valueOfPlayerHand += currentCardPlayer.value;
        String dilerHandString = "";
        for (DeckOfCards.PartsOfCard element: dilerHand) {
            dilerHandString += element.suit + " " + element.name + "; ";
        }

        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setReplyMarkup(getKeyboard());


        if (valueOfPlayerHand > 21){
            answer.setText(valueOfPlayerHand + "over 21, you lose. new game started");
            yourHand.clear();
            valueOfPlayerHand = 0;
            currentDeck = new DeckOfCards();
        }else {
            answer.setText("card added, your points: " + valueOfPlayerHand  + "\n" +"diler cards: ");
        }
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private  ReplyKeyboardMarkup getKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        if  (valueOfPlayerHand > 21){
            keyboard.clear();
            keyboardFirstRow.add("your cards");
        }else {
            for (DeckOfCards.PartsOfCard element : yourHand) {
                keyboardFirstRow.add(element.suit + " " + element.name);
            }
        }

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add("/take card");
        keyboardSecondRow.add("/exit");
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
}

