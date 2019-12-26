package sanekels.bendergamblerbot;

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
    private static final String description = "stop Taking Cards";
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
            dilerHand.addCard(currentDeck.takeOneCard());
        }

        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setReplyMarkup(new UsefulMethods().getKeyboard(commandIdentifier, yourHand));

        if (dilerHand.intValueOfHand() <= 21) {
            if (dilerHand.intValueOfHand() == 21 && yourHand.intValueOfHand() < 21) {
                answer.setText("your points: " + yourHand.intValueOfHand() + "\n" + "diler cards: " + dilerHand.stringValueOfHand() +
                        "\ndealer has blackJack, you lose");
            } else if (yourHand.intValueOfHand() < dilerHand.intValueOfHand()) {
                answer.setText("your points: " + yourHand.intValueOfHand() + "\n" + "diler cards: " + dilerHand.stringValueOfHand() +
                        "\n you lose");
            } else if (yourHand.intValueOfHand() > dilerHand.intValueOfHand() && yourHand.intValueOfHand() < 21) {
                answer.setText("your points: " + yourHand.intValueOfHand() + "\n" + "diler cards: " + dilerHand.stringValueOfHand() +
                        "\nyou win!");
            } else if (yourHand.intValueOfHand() == 21 && dilerHand.intValueOfHand() < 21) {
                answer.setText("your points: " + yourHand.intValueOfHand() + "\n" + "diler cards: " + dilerHand.stringValueOfHand() +
                        "\nyou have blackJack, you win!");
            } else {
                answer.setText("your points: " + yourHand.intValueOfHand() + "\n" + "diler cards: " + dilerHand.stringValueOfHand() +
                        "\ndraw");
            }
        } else {
            answer.setText("your points: " + yourHand.intValueOfHand() + "\n" + "diler cards: " + dilerHand.stringValueOfHand() +
                    "\nyou win!");
        }
        dilerHand.clearCards();
        yourHand.clearCards();
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}