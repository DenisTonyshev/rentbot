package telegrammrentalbot.rentbot.botAbilities;

import org.telegram.telegrambots.meta.api.methods.send.*;
import java.util.*;

public interface IBotAbilities {
    void informAll(String text);
    Long sendFullMessageToUser(SendPhoto photo);
    Long sendTextMessageToUserById(String text, long userId);
    SendMessage sendButtonMenu(Map<String,List<String>> buttons, long userId);
}
