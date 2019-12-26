import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class StopTakeCardBlackJack extends BotCommand {
    private static final String commandIdentifier = "stop";
    private static final String description = "stopTakingCards";
    private BlackJackGamers yourHand;
    private BlackJackGamers dilerHand;
    private DeckOfCards currentDeck;

    public StopTakeCardBlackJack(BlackJackGamers yourHand1, BlackJackGamers dilerHand1, DeckOfCards currentDeck1) {
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
        while (dilerHand.intValueOfHand() < 17) {
            dilerHand.addCard(currentDeck.TakeOneCard());
        }
        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setReplyMarkup(getKeyboard());

        if (dilerHand.intValueOfHand() == 21 && yourHand.intValueOfHand() != 21) {
            answer.setText("your points: " + yourHand.intValueOfHand() + "\n" + "diler cards: " + dilerHand.stringValueOfHand() +
                    "\ndealer has blackJack, you lose");
        }
        if (dilerHand.intValueOfHand() == yourHand.intValueOfHand()) {
            answer.setText("your points: " + yourHand.intValueOfHand() + "\n" + "diler cards: " + dilerHand.stringValueOfHand() + "\ndraw");
        }
        if (dilerHand.intValueOfHand() > 21 || dilerHand.intValueOfHand() < yourHand.intValueOfHand()) {
            answer.setText("your points: " + yourHand.intValueOfHand() + "\n" + "diler cards: " + dilerHand.stringValueOfHand() + "\nyou win!");
        }
        if (dilerHand.intValueOfHand() < yourHand.intValueOfHand() && yourHand.intValueOfHand() == 21) {
            answer.setText("your points: " + yourHand.intValueOfHand() + "\n" + "diler cards: " + dilerHand.stringValueOfHand() +
                    "\nyou have blackJack, you win!");
        }
        if ((dilerHand.intValueOfHand() > yourHand.intValueOfHand() && dilerHand.intValueOfHand() < 22) && dilerHand.intValueOfHand() != 21) {
            answer.setText("your points: " + yourHand.intValueOfHand() + "\n" + "diler cards: " + dilerHand.stringValueOfHand() + "\n you lose");
        }
        dilerHand.clearCards();
        yourHand.clearCards();
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private ReplyKeyboard getKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        for (DeckOfCards.PartsOfCard element : yourHand.AllCards()) {
            keyboardFirstRow.add(element.suit + " " + element.name);
        }
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add("/jack");
        keyboardSecondRow.add("/exit");
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
}