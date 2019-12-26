import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class BenderGamblerBot extends TelegramLongPollingCommandBot {
    private static final String BOT_USERNAME = "BenderGamblerBot";
    private static final String BOT_TOKEN = System.getenv("BOT_TOKEN_CLOSED");
    public BlackJackGamers yourHand = new BlackJackGamers();
    public BlackJackGamers dilerHand = new BlackJackGamers();
    public DeckOfCards currentDeck = new DeckOfCards();

    BenderGamblerBot(DefaultBotOptions botOptions) {
        super(botOptions, BOT_USERNAME);
        register(new BlackJackStart(yourHand, dilerHand, currentDeck));
        register(new TakeCardBlackJack(yourHand, dilerHand, currentDeck));
        register(new StopTakeCardBlackJack(yourHand, dilerHand, currentDeck));
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("hello, choose game");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add("/jack");
        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
