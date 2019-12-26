package sanekels.bendergamblerbot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class UsefulMethods {
    public ReplyKeyboardMarkup getKeyboard(String identifier, BlackJackGamers yourHand) {
        BlackJackGamers yourHandcopy = yourHand;
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        for (TheCard element : yourHandcopy.AllCards()) {
            keyboardFirstRow.add(element.suit + " " + element.name);
        }
        if (yourHandcopy.intValueOfHand() > 21) {
            keyboard.clear();
        }
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        if (identifier == "jack") {
            keyboardSecondRow.add("/take card");
            keyboardSecondRow.add("/exit");
            keyboardSecondRow.add("/stop");
        }
        if (identifier == "take") {
            if (yourHandcopy.intValueOfHand() >= 21) {
                keyboardSecondRow.add("/jack ");
                keyboardSecondRow.add("/exit");
            } else {
                keyboardSecondRow.add("/take card");
                keyboardSecondRow.add("/stop");
            }
        }
        if (identifier == "stop") {
            keyboardSecondRow.add("/jack");
            keyboardSecondRow.add("/exit");
        }
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
}
