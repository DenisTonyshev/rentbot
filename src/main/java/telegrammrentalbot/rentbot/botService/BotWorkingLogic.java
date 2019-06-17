package telegrammrentalbot.rentbot.botService;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegrammrentalbot.rentbot.botAbilities.*;
import telegrammrentalbot.rentbot.constants.MenuB;

import javax.annotation.PostConstruct;

@Component
public class BotWorkingLogic extends TelegramLongPollingBot {
    private long currentmessageId;
    private long chatId;
    private IBotAbilities botDo = new AbilitiesImplementation();

    @PostConstruct
    private void registerBot() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Bean
    private TelegramBotsApi getBootApi() {
        return new TelegramBotsApi();
    }

    @Value("${bot.token}")
    private String botToken;
    @Value("${bot.name}")
    private String botName;

    @Override
    public void onUpdateReceived(Update update) {

        if (update.getCallbackQuery() != null) {
            System.out.println(update.getCallbackQuery().toString());
            System.out.println(update.getCallbackQuery().getData());
            botDo.dellMessage(this.currentmessageId, this.chatId);
        } else {
            currentmessageId = update.getMessage().getMessageId();
            chatId = update.getMessage().getChatId();
            Long userId = update.getMessage().getChatId();
            String message = update.getMessage().getText();
            if (message.equals("R")) {
                SendMessage sendMsg = new SendMessage();
                Message msg = new Message();
                try {
                    SendMessage sendMessage = botDo.sendButtonMenu(new MenuB().getStartingMenu(), userId);
                    msg = execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
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


}

