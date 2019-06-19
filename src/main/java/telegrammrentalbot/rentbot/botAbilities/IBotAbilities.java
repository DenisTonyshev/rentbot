package telegrammrentalbot.rentbot.botAbilities;

import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import telegrammrentalbot.rentbot.dto.RentObjectDto;

import java.util.*;

public interface IBotAbilities {

    DeleteMessage dellMessage(long messageId, long chatId);
    void informAll(String text);
    Long sendFullMessageToUser(SendPhoto photo);
    Long sendTextMessageToUserById(String text, long userId);
    SendMessage sendButtonMenu(Map<String,List<String>> buttons, long userId);
    SendMessage sendMenu(Message msg);
SendMessage sendMessageToUser(Message msg, String text);
    SendMessage sendRegionMenu(Message message);

    SendMessage fillTheRentAD(Message message);

    List<SendPhoto> sendAllAds(Message message, List<RentObjectDto> ads);

    RentObjectDto parseTheText(Message message);

}
