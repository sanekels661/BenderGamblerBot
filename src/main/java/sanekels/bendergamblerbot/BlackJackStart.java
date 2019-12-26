package sanekels.bendergamblerbot;

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

public class BlackJackStart extends BotCommand {

    private static final String commandIdentifier = "jack";
    private static final String description = "start Game BlackJack";
    private BlackJackGamers yourHand;
    private BlackJackGamers dilerHand;
    private DeckOfCards currentDeck;

    public BlackJackStart(BlackJackGamers yourHand1, BlackJackGamers dilerHand1, DeckOfCards currentDeck1) {
        super(commandIdentifier, description);
        yourHand = yourHand1;
        dilerHand = dilerHand1;
        currentDeck = currentDeck1;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        currentDeck.refreshDeck();
        yourHand.clearCards();
        dilerHand.clearCards();
        yourHand.addCard(currentDeck.takeOneCard());
        yourHand.addCard(currentDeck.takeOneCard());
        dilerHand.addCard(currentDeck.takeOneCard());
        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setReplyMarkup(new UsefulMethods().getKeyboard(commandIdentifier, yourHand));
        answer.setText("game started \n" + "card added, your points: " + yourHand.intValueOfHand() + "\n" + "diler cards: " + dilerHand.stringValueOfHand());
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
