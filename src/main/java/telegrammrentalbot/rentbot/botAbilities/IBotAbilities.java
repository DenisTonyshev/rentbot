package telegrammrentalbot.rentbot.botAbilities;

import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.*;

public interface IBotAbilities {

    DeleteMessage dellMessage(long messageId, long chatId);
    void informAll(String text);
    Long sendFullMessageToUser(SendPhoto photo);
    Long sendTextMessageToUserById(String text, long userId);
    SendMessage sendButtonMenu(Map<String,List<String>> buttons, long userId);
    SendMessage sendMenu(Message msg);

    SendMessage sendRegionMenu(Message message);

    SendMessage sendAllAds(Message message);
}
