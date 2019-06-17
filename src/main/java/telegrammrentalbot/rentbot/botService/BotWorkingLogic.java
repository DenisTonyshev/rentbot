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
import telegrammrentalbot.rentbot.constants.Menu;
import javax.annotation.PostConstruct;

@Component
public class BotWorkingLogic extends TelegramLongPollingBot{

    IBotAbilities botDo = new AbilitiesImplementation();

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
        CallbackQuery callbackQuery = update.getCallbackQuery();
        System.out.println(callbackQuery.getData());

        String message = update.getMessage().getText();
        if (message.equals("R")) {
            SendMessage sendMsg = new SendMessage();
            Message msg = new Message();
            SendMessage a = sendMsg.setText("AAAAAAAAAAAAAA").setChatId((long) 153991281);
            try {
                SendMessage sendMessage = botDo.sendButtonMenu(new Menu().getStartingMenu(), 153991281);
                msg =execute(sendMessage);
                msg = execute(a);
            } catch (TelegramApiException e) {
                e.printStackTrace();
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

