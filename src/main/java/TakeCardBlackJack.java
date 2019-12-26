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

public class TakeCardBlackJack extends BotCommand {

    private static final String commandIdentifier = "take";
    private static final String description = "take card";
    private DeckOfCards currentDeck;
    private BlackJackGamers yourHand;
    private BlackJackGamers dilerHand;

    public TakeCardBlackJack(BlackJackGamers yourHand1, BlackJackGamers dilerHand1, DeckOfCards currentDeck1) {
        super(commandIdentifier, description);
        yourHand = yourHand1;
        dilerHand = dilerHand1;
        currentDeck = currentDeck1;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        if (currentDeck.thisDeck.size() < 26) {
            currentDeck = new DeckOfCards();
        }
        yourHand.addCard(currentDeck.TakeOneCard());
        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setReplyMarkup(getKeyboard());
        if (yourHand.intValueOfHand() == 21) {
            answer.setText("your points: " + yourHand.intValueOfHand() + "; blackJack. you win");
            currentDeck = new DeckOfCards();
        }
        if (yourHand.intValueOfHand() > 21) {
            answer.setText(yourHand.intValueOfHand() + " over 21, you lose");
            currentDeck = new DeckOfCards();
        }
        else {
            answer.setText("card added, your points: " + yourHand.intValueOfHand() + "\n" + "diler cards: " + dilerHand.stringValueOfHand());
        }
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private ReplyKeyboardMarkup getKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        for (DeckOfCards.PartsOfCard element : yourHand.AllCards()) {
            keyboardFirstRow.add(element.suit + " " + element.name);
        }
        if (yourHand.intValueOfHand() >= 21) {
            keyboardSecondRow.add("/jack ");
            keyboardSecondRow.add("/exit");
        } else {
            keyboardSecondRow.add("/take card");
            keyboardSecondRow.add("/stop");
        }
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
}

