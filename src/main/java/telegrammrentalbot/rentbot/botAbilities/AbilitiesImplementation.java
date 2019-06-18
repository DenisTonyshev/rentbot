package telegrammrentalbot.rentbot.botAbilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import telegrammrentalbot.rentbot.dto.RentObjectDto;
import telegrammrentalbot.rentbot.inLineBuilder.InlineKeyboardBuilder;
import telegrammrentalbot.rentbot.service.IMongoDBService;
import telegrammrentalbot.rentbot.service.IMongoDBUserService;

import java.util.*;

@Component
public class AbilitiesImplementation implements IBotAbilities {
    private HashMap<String, List<String>> mapIsr = new HashMap<>();

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
        mapFiller(mapIsr);
        List<String> regions = new ArrayList<>();
        regions.add("NORTH");
        regions.add("SOUTH");
        regions.add("CENTER");
        menuConstructor(message, regions);
        return menuConstructor(message, regions);
    }

    @Override
    public SendMessage sendRegionMenu(Message message) {
        mapFiller(mapIsr);
        return menuConstructor(message, mapIsr.get(message.getText()));
    }

    @Override
    public List<SendPhoto> sendAllAds(Message message, List<RentObjectDto> ads) {
        List<SendPhoto> rents = new ArrayList<>();
        SendPhoto msg = new SendPhoto();
        for (RentObjectDto o : ads) {
        StringBuilder sb = new StringBuilder();
            msg.setChatId(message.getChatId()).setPhoto("https://www.moya-planeta.ru/files/holder/9c/fa/9cfa0d67456298d03c5bb93c91281da7.jpg")
                    .setCaption(sb.append(o.getDescription()).append("\n").append(o.getAddress()).append("\n").append(o.getContacts()).toString());
            rents.add(msg);
        }
        return rents;
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

    private void mapFiller(HashMap<String, List<String>> map) {
        if (map.isEmpty()) {
            map.put("SOUTH", new ArrayList<>());
            map.put("CENTER", new ArrayList<>());
            map.put("NORTH", new ArrayList<>());
            map.get("SOUTH").addAll(Arrays.asList("BEER_SHEBA", "ASHDOD", "ASHKELON", "MAIN_MENU"));
            map.get("NORTH").addAll(Arrays.asList("HAIFA", "AKKO", "NETANYA", "RAANANNA", "HERZLIA", "CEISARIA", "MAIN_MENU"));
            map.get("CENTER").addAll(Arrays.asList("TEL_AVIV", "PETAH_TIKVA", "RAMAT_GAN", "MAIN_MENU"));
        }
    }

}
