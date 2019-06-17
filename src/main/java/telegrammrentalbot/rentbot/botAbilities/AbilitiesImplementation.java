package telegrammrentalbot.rentbot.botAbilities;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegrammrentalbot.rentbot.inLineBuilder.InlineKeyboardBuilder;
import java.util.*;

@Component
public class AbilitiesImplementation implements IBotAbilities {
private InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();

    @Override
    public void informAll(String text) {

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
    public SendMessage sendButtonMenu(Map<String, String> buttons, long userId) {
        SendMessage menu = new InlineKeyboardBuilder().create(userId)
                .setText("MENU")
                .setChatId(userId)
                .rowBuilder(buttons)
                .build();
        return menu;
    }

}
