package telegrammrentalbot.rentbot.service;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.*;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class BotServiceClass extends TelegramLongPollingBot implements IBotServiceInterface {

    @PostConstruct
    public void registerBot() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    //STATEFULL IMPL
    Map<String, String> buttonsMap = new HashMap<>();

    //==========================================
    @Bean
    private TelegramBotsApi getBootApi() {
        return new TelegramBotsApi();
    }

    @Value("${bot.token}")
    private String botToken;
    @Value("${bot.name}")
    private String botName;


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
    public Long sendButtonMenu(Map<String, String> buttons, long userId) {
        //STATEFULL
        buttonsMap.put("ЮГ", "!!!!!!!!!!!");
        buttonsMap.put("ЗАПАД", "КАК ЗАПАД");
        buttonsMap.put("ВОСТОК", "ДЕЛО ТОНКОЕ");
        buttonsMap.put("СЕВЕР", "СЕВЕРЯНЕ ВПЕРЕД");

        SendMessage inlineKeyboardBuilder = new InlineKeyboardBuilder().create(userId)
                .setText("MENU")
                .setChatId(userId)
                .rowBuilder(buttonsMap)
                .build();
        try {
            Message message = execute(inlineKeyboardBuilder);
            return Long.valueOf(message.getMessageId());

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return -1L;
    }

    //==================== Telegramm Polling methods
    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        if (message.equals("R")) {
            SendMessage sendMsg = new SendMessage();
            SendMessage a = sendMsg.setText("AAAAAAAAAAAAAA").setChatId((long) 153991281);
            sendButtonMenu(buttonsMap, 53451645);
            try {
                Message msg = execute(a);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
//        (update.getMessage().getChatId().toString(), message);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    //========================================================


    private class InlineKeyboardBuilder {

        private Long chatId;
        private String text;

        private List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        private List<InlineKeyboardButton> row = null;

        public InlineKeyboardBuilder() {
        }

        private InlineKeyboardBuilder create() {
            InlineKeyboardBuilder builder = new InlineKeyboardBuilder();
            return builder;
        }

        public InlineKeyboardBuilder create(Long chatId) {
            InlineKeyboardBuilder builder = new InlineKeyboardBuilder();
            builder.setChatId(chatId);
            return builder;
        }

        public InlineKeyboardBuilder setText(String text) {
            this.text = text;
            return this;
        }

        public InlineKeyboardBuilder setChatId(Long chatId) {
            this.chatId = chatId;
            return this;
        }

        public InlineKeyboardBuilder row() {
            this.row = new ArrayList<>();
            return this;
        }

        public InlineKeyboardBuilder button(String text, String callbackData) {
            row.add(new InlineKeyboardButton().setText(text).setCallbackData(callbackData));
            return this;
        }

        public InlineKeyboardBuilder button(Map<String, String> buttons) {
            buttons.forEach((k, v) -> row.add(new InlineKeyboardButton().setText(k).setCallbackData(v)));
            return this;
        }

        public InlineKeyboardBuilder rowBuilder(Map<String, String> buttons) {
            long i = 1;
            Iterator<Map.Entry<String, String>> it = buttons.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> pair = it.next();
                if (i % 2 != 0) {
                    row();
                }
                button(EmojiParser.parseToUnicode(pair.getKey()), EmojiParser.parseToUnicode(pair.getValue()));
                if (i % 2 == 0) {
                    endRow();
                }
                i++;
            }
            return this;
        }

        public InlineKeyboardBuilder endRow() {
            this.keyboard.add(this.row);
            this.row = null;
            return this;
        }

        public SendMessage build() {
            SendMessage message = new SendMessage();

            message.setChatId(chatId);
            message.setText(text);

            InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

            keyboardMarkup.setKeyboard(keyboard);
            message.setReplyMarkup(keyboardMarkup);

            return message;
        }

    }
}

