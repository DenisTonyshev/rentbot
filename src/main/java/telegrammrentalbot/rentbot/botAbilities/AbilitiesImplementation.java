package telegrammrentalbot.rentbot.botAbilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import telegrammrentalbot.rentbot.inLineBuilder.InlineKeyboardBuilder;
import telegrammrentalbot.rentbot.service.IMongoDBService;
import telegrammrentalbot.rentbot.service.IMongoDBUserService;

import java.util.*;

@Component
public class AbilitiesImplementation implements IBotAbilities {

    @Autowired
    IMongoDBService dataBase;
    @Autowired
    IMongoDBUserService userBase;

    @Override
    public void informAll(String text) {

    }

    @Override
    public DeleteMessage dellMessage(long messageId, long chatId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setMessageId((int) messageId);
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

    @Override
    public SendMessage sendMenu(Message message) {
        List<String> regions = new ArrayList<>();
        regions.add("NORTH");
        regions.add("SOUTH");
        regions.add("CENTER");
        menuConstructor(message, regions);
        return menuConstructor(message, regions);
    }

    @Override
    public SendMessage sendRegionMenu(Message message) {
        List<String> cities = new ArrayList<>();
        switch (message.getText()) {
            case "NORTH":
                cities.add("HAIFA");
                cities.add("AKKO");
                cities.add("NETANYA");
                cities.add("RAANANA");
                cities.add("HERZLIYA");
                cities.add("CEISARIYA");
                cities.add("BACK TO MAIN MENU");
                break;
            case "SOUTH":
                cities.add("BEER_SHEBA");
                cities.add("ASHKELON");
                cities.add("ASHDOD");
                cities.add("BACK TO MAIN MENU");
                break;
            case "CENTER":
                cities.add("TEL_AVIV");
                cities.add("PETAH_TIKVA");
                cities.add("RAMAT_GAN");
                cities.add("BACK TO MAIN MENU");
                break;
        }
        return menuConstructor(message, cities);
    }

    @Override
    public SendMessage sendAllAds(Message message) {

        return null;
    }


    private SendMessage menuConstructor(Message message, List<String> name) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();

        for (int i = 0; i < name.size(); i++) {
            if (i <= 2) {
                keyboardFirstRow.add(name.get(i));
            }
            if (i > 2) {
                keyboardSecondRow.add(name.get(i));
            }
        }
//
//
//        // Добавляем кнопки в первую строчку клавиатуры
//        keyboardFirstRow.add("NORTH");
//        keyboardFirstRow.add("CENTER");
//        keyboardFirstRow.add("SOUTH");

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setText("MENU");
        return sendMessage.setChatId(message.getChatId().toString());

    }

//    private void
}
