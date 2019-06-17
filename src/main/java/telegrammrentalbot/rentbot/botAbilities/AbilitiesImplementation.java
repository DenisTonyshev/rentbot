package telegrammrentalbot.rentbot.botAbilities;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import telegrammrentalbot.rentbot.constants.MenuB;
import telegrammrentalbot.rentbot.inLineBuilder.InlineKeyboardBuilder;
import java.util.*;

@Component
public class AbilitiesImplementation implements IBotAbilities {
private InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
MenuB menuB = new MenuB();

    @Override
    public void informAll(String text) {

    }

    @Override
    public DeleteMessage dellMessage(int messageId, long chatId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setMessageId(messageId);
        deleteMessage.setChatId(chatId);
        return deleteMessage;
    }

    @Override
    public Long sendFullMessageToUser(SendPhoto photo) {
        return null;
    }

    @Override
    public Long sendTextMessageToUserById(String text, long userId) {
        return null;
    }

    @Override
    public SendMessage sendButtonMenu(Map<String, List<String>> buttons, long userId) {
        return new InlineKeyboardBuilder().create(userId)
                .setText("MENU")
                .setChatId(userId)
                .rowBuilder(buttons)
                .build();
    }

}
