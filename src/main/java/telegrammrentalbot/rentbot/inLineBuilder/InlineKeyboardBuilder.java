package telegrammrentalbot.rentbot.inLineBuilder;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.*;

public class InlineKeyboardBuilder {

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