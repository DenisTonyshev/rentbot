package telegrammrentalbot.rentbot.botService;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegrammrentalbot.rentbot.botAbilities.*;
import telegrammrentalbot.rentbot.dto.*;
import telegrammrentalbot.rentbot.service.*;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static telegrammrentalbot.rentbot.constants.consts.REGION_CENTER;
import static telegrammrentalbot.rentbot.constants.consts.REGION_SOUTH;

@Component
public class BotWorkingLogic extends TelegramLongPollingBot {

    private static int countID = 0;
    private static final Object countLock = new Object();

    private void incrementCount() {
        synchronized (countLock) {
            countID++;
        }
    }

    private int counter = 0;
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
//        System.out.println(update.getCallbackQuery().getData());
        if (message.getText().equals("/start")) {
            UserDto user = new UserDto(message.getFrom().getId(), message.getFrom().getFirstName(), message.getFrom().getBot(),
                    message.getFrom().getLastName(), message.getFrom().getUserName(), message.getFrom().getLanguageCode());
            userBase.addUser(user);
        } else {
            //ADD NEW POST TO DB

            if (message.getText().split("\n")[0].equals("/post")) {
                if (counter > 1) {
                    SendMessage msg = new SendMessage();
                    msg.setText("НУ ВОТ ХУЛИ ТЫ ТЫЧИШЬ И ТЫЧИШЬ!");
                    msg.setChatId(message.getChatId());
                    counter = 0;
                    try {
                        execute(msg);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (counter == 1) {
                        RentObjectDto rentObjectDto = botDo.parseTheText(message);
                        rentObjectDto.setPhoto(new ArrayList<>());
                        dataBase.createRent(rentObjectDto);
                        SendMessage msg = new SendMessage();
                        msg.setText("НУ ВОТ И ВСЁ, ПОТОМ БУДУТ ФОТО!");
                        msg.setChatId(message.getChatId());
                        execute(msg);
                        counter = 0;
                    } else if (counter == 0) {
                        counter += 1;
                        execute(botDo.fillTheRentAD(message));
                    }
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

//            TEST_FILL_THE_BASE(message);
            } else if (message.hasText())
                try {
                    switch (message.getText()) {
                        case "NORTH":
                            System.out.println(message.getText());
                        case "SOUTH":
                            System.out.println(message.getText());
                        case "CENTER":
                            System.out.println(message.getText());
                            execute(botDo.sendRegionMenu(message));
                            break;
                        case "TEL-AVIV":
                            List<RentObjectDto> south = dataBase.allAreaRents(REGION_CENTER);
                            List<SendPhoto> sendPhotos = botDo.sendAllAds(message, south);
                            for (SendPhoto o : sendPhotos) {
                                execute(o);
                            }
                        default:
                            counter = 0;
                            System.out.println(message.getText());
                            execute(botDo.sendMenu(message));
                            break;
                    }
                } catch (
                        TelegramApiException e) {
                    e.printStackTrace();
                }
        }
    }

    //ПРОВЕРКА ЗАПОЛНЕНИЯ БАЗЫ ОБЬЯВЛЕНИЙ
    private void TEST_FILL_THE_BASE(Message message) {
        List<String> paths = new ArrayList<>();
        paths.add("ФОТО ОБЪЕКТА ИЛИ ФОТОГРАФИИ, КОЛЛАЖ");

        for (int i = 1; i < 11; i++) {
            RentObjectDto objectDto = new RentObjectDto();
            objectDto.setUserId(message.getFrom().getId());
            objectDto.setDescription("Большая" + i + " комнатная квартира,полнoстью мебелираванная с большой обустроенной крышей");
            objectDto.setContacts("+972053" + (i - 1) + ThreadLocalRandom.current().ints(100000, 999999).findFirst().getAsInt());
            objectDto.setPhoto(paths);
            objectDto.setPostDate(LocalDate.now());
            objectDto.setPrice(ThreadLocalRandom.current().ints(2500, 10000).findFirst().getAsInt());
            objectDto.setActive(true);
            objectDto.setAddress("Rahvat HaRosh 2-" + i);
            objectDto.setArea(REGION_SOUTH);
            objectDto.setCityName("BEERSHEBA");

            dataBase.createRent(objectDto);
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

}


