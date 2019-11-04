import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class BenderGamblerBot extends TelegramLongPollingCommandBot {
    private static final String BOT_USERNAME = "BenderGamblerBot";
    private static final String BOT_TOKEN = "937156335:AAGQuM2Vdbvdsofa0Gv0tNfW16sm-RswcM0";

    BenderGamblerBot(DefaultBotOptions botOptions) {
        super(BOT_USERNAME);
        register(new BlackJack());
        register(new TakeCard());
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("hello, take card to start");
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
