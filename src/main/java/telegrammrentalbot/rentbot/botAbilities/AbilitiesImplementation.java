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
import telegrammrentalbot.rentbot.service.*;

import java.time.LocalDate;
import java.util.*;

import static telegrammrentalbot.rentbot.constants.consts.*;
//import static telegrammrentalbot.rentbot.repo.iAmazonS3.Tatatata;

@Component
public class AbilitiesImplementation implements IBotAbilities {

    private HashMap<String, List<String>> mapIsr = new HashMap<>();

    {
        mapFiller(mapIsr);
    }

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
        List<String> regionsMenu = REGIONS_MENU;
        return menuConstructor(message, regionsMenu, MAIN_MENU);
    }

    @Override
    public SendMessage sendMessageToUser(Message message, String text) {
        SendMessage sm = new SendMessage();
        sm.setChatId(message.getChatId());
        sm.setText(text);
        return sm;
    }

    @Override
    public SendMessage sendRegionMenu(Message message) {
        return menuConstructor(message, mapIsr.get(message.getText()), "CITIES");
    }

    @Override
    public SendMessage fillTheRentAD(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        StringBuilder sb = new StringBuilder();
        sb.append("Description: ")
                .append("ТУТ ТВОЕ ОПИСАНИЕ \n")
                .append("Contacts: ")
                .append("ТВОЙ ТЕЛЕФОН \n")
                .append("Price: ")
                .append("УКАЖИ СВОЮ ЦЕНУ в ШЕКЕЛЯХ \n")
                .append("Address: ")
                .append("АДРЕС ГДЕ ПОСМОТРЕТЬ \n")
                .append("Region: ")
                .append("В КАКОМ РЕГИОНЕ(NORTH,SOUTH,CENTER) \n")
                .append("City: ")
                .append("В КАКОМ ГОРОДЕ \n")
                .append("И на ПОСЛЕДОК ЕСЛИ есть ФОТО(ФОТОКОЛЛАЖ) Приложи слудующим сообщением");
        return sendMessage.setText("НУ ДАВАЙ ПОДАДИМ ОБЬЯВЛЕНИЕ ВОТ ТЕБЕ ОБРАЗЕЦ ПОСТАРАЙСЯ ЗАПОЛНИТЬ ТАКЖЕ" + "\n" + sb);
    }

    @Override
    public List<SendPhoto> sendAllAds(Message message, List<RentObjectDto> ads) {
        List<SendPhoto> rents = new ArrayList<>();
        for (RentObjectDto o : ads) {
            SendPhoto msg = new SendPhoto();
            try {
                //=================================TODO
//                File tatatata = Tatatata();
                //=================================^
                msg.setChatId(message.getChatId())
                        .setPhoto("https://stickeroid.com/uploads/pic/full-tlgrmadd/thumb/stickeroid_5bf55072f275d.png")
                        .setCaption(buildTheStringDescription(o));
                rents.add(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rents;
    }

    @Override
    public RentObjectDto parseTheText(Message message) {
        String text = message.getText();
        List<String> textFromUser = new ArrayList<>();
        String[] split = text.split("\n");
        for (String o : split) {
            if (o.equals("/post")) {
                continue;
            }
            String[] split1 = o.split(":");
            textFromUser.add(split1[1].trim().toUpperCase());
        }
        Integer id = message.getFrom().getId();
        System.out.println(textFromUser);
        System.out.println(advId);
        return new RentObjectDto(advId.getAndIncrement(), id, textFromUser.get(0).toLowerCase(), textFromUser.get(1), LocalDate.now(), Double.parseDouble(textFromUser.get(2)), true, textFromUser.get(3).toLowerCase(), textFromUser.get(4), textFromUser.get(5), null);
    }

    private String buildTheStringDescription(RentObjectDto o) {
        return new StringBuilder()
                .append("Description: ")
                .append(o.getDescription())
                .append("\n")
                .append("Address: ")
                .append(o.getAddress())
                .append("\n")
                .append("Price: ")
                .append(o.getPrice())
                .append("\n")
                .append("Contacts: ")
                .append(o.getContacts())
                .toString();
    }

    private SendMessage menuConstructor(Message message, List<String> name, String messageText) {
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

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setText(messageText);
        return sendMessage.setChatId(message.getChatId().toString());

    }

    private void mapFiller(HashMap<String, List<String>> map) {
        if (map.isEmpty()) {
            map.put(REGION_SOUTH, SOUTH_CITIES);
            map.put(REGION_CENTER, CENTER_CITIES);
            map.put(REGION_NORTH, NORTH_CITIES);
        }
    }

}
