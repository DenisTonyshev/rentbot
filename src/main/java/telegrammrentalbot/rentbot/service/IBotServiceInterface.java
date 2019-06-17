package telegrammrentalbot.rentbot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import java.util.*;

public interface IBotServiceInterface {
    void informAll(String text);
    Long sendFullMessageToUser(SendPhoto photo);
    Long sendTextMessageToUserById(String text, long userId);
    Long sendButtonMenu(Map<String,String> buttons, long userId);
}
