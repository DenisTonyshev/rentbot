package telegrammrentalbot.rentbot.botService;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegrammrentalbot.rentbot.botAbilities.*;
import telegrammrentalbot.rentbot.dto.UserDto;
import telegrammrentalbot.rentbot.service.IMongoDBService;
import telegrammrentalbot.rentbot.service.IMongoDBUserService;
import telegrammrentalbot.rentbot.service.MongoDBServiceImpl;


import javax.annotation.PostConstruct;

@Component
public class BotWorkingLogic extends TelegramLongPollingBot {
    //    private long currentmessageId;
//    private long chatId;
    private IBotAbilities botDo = new AbilitiesImplementation();
    @Autowired
    IMongoDBService dataBase;
    @Autowired
    IMongoDBUserService userBase;


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

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message.getText().equals("/start")) {
            UserDto user = new UserDto(message.getFrom().getId(), message.getFrom().getFirstName(), message.getFrom().getBot(),
                    message.getFrom().getLastName(), message.getFrom().getUserName(), message.getFrom().getLanguageCode());
            userBase.addUser(user);
        } else if (message.hasText())
            try {
                switch (message.getText()) {
                    case "SAVEAD":

                    case "NORTH":
                        System.out.println(message.getText());
                        execute(botDo.sendRegionMenu(message));
                        break;
                    case "SOUTH":
                        System.out.println(message.getText());
                        execute(botDo.sendRegionMenu(message));
                        break;
                    case "CENTER":
                        System.out.println(message.getText());
                        execute(botDo.sendRegionMenu(message));
                        break;
                    default:
                        System.out.println(message.getText());
                        try {
                            execute(botDo.sendMenu(message));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;

                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
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


