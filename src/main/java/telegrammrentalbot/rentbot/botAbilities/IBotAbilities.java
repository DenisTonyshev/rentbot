package telegrammrentalbot.rentbot.botAbilities;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.*;

public interface IBotAbilities {
    void informAll(String text);
    Long sendFullMessageToUser(SendPhoto photo);
    Long sendTextMessageToUserById(String text, long userId);
    SendMessage sendButtonMenu(Map<String,String> buttons, long userId);
}
