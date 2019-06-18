package telegrammrentalbot.rentbot.botService;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegrammrentalbot.rentbot.botAbilities.*;
import telegrammrentalbot.rentbot.dto.*;
import telegrammrentalbot.rentbot.service.*;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;

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
        }
        if (message.getText().equals("post")) {
            List<String> paths = new ArrayList<>();
            paths.add("ПУТЬ К ФАЙЛУ");
            for (int i = 0; i <9 ; i++) {
                RentObjectDto objectDto = new RentObjectDto((long)i, message.getFrom().getId(), "TESTOVOE OBIVLENIE", "+79995552525", paths, LocalDate.now(), new Random().nextDouble()*10000,true, "Shaula melach"+i, "SOUTH","TEL_AVIV");
                dataBase.createRent(objectDto);
            }
        } else if (message.hasText())
            try {
                switch (message.getText()) {
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
                    case "TEL_AVIV":
                        List<RentObjectDto> south = dataBase.allAreaRents("SOUTH");
                        List<SendPhoto> sendPhotos = botDo.sendAllAds(message, south);
                        for (SendPhoto o: sendPhotos) {
                            execute(o);
                        }
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


