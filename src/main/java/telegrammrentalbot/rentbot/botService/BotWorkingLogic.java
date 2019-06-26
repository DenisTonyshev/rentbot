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
import telegrammrentalbot.rentbot.dto.*;
import telegrammrentalbot.rentbot.service.*;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static telegrammrentalbot.rentbot.constants.consts.*;

@Component
public class BotWorkingLogic extends TelegramLongPollingBot {


    private IBotAbilities botDo = new AbilitiesImplementation();
    private IBotFillTheRentAD botFill = new FillTheRentForm();

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
                    message.getFrom().getLastName(), message.getFrom().getUserName(), message.getFrom().getLanguageCode(), new RentObjectDto(), 0);
            userBase.addUser(user);
        } else {
            //ADD NEW POST TO DATAbase AdvancedStepByStep
            if (message.getText().trim().substring(0, 5).equals("/post")) {
                postTheRentalAd(message, userBase.getUserById(message.getFrom().getId()));
            }
            //ADD NEW POST TO DataBase Primitive
//            if (message.getText().split("\n")[0].equals("/post")) {
//                if (counter > 1) {
//                    SendMessage msg = new SendMessage();
//                    msg.setText("НУ ВОТ ХУЛИ ТЫ ТЫЧИШЬ И ТЫЧИШЬ!");
//                    msg.setChatId(message.getChatId());
//                    counter = 0;
//                    try {
//                        execute(msg);
//                    } catch (TelegramApiException e) {
//                        e.printStackTrace();
//                    }
//                }
//                try {
//                    if (counter == 1) {
//                        RentObjectDto rentObjectDto = botDo.parseTheText(message);
//                        rentObjectDto.setPhoto(new ArrayList<>());
//                        dataBase.createRent(rentObjectDto);
//                        execute(botDo.sendMessageToUser(message,"Ну вот пока и всё"));
//                        counter = 0;
//                    } else if (counter == 0) {
//                        counter += 1;
//                        execute(botDo.fillTheRentAD(message));
//                    }
//                } catch (TelegramApiException e) {
//                    e.printStackTrace();
//                }


//            TEST_FILL_THE_BASE(message);
            else if (message.hasText())
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

    private void postTheRentalAd(Message message, UserDto user) {
        int counter = user.getCounter();
        RentObjectDto rentalAd = user.getRentalAd();
        try {
            switch (counter) {
                //ВСТУПЛЕНИЕ И ПОЯСНЕНИЕ
                case 0:
                    execute(botDo.sendMessageToUser(message, "ПОГНАЛИ В УВЛЕКАТЕЛЬНОЕ ПРИКЛЮЧЕНИЕ на 15 минут"));
                    execute(botDo.sendMessageToUser(message, "не забывай писать /post в начале"));
                    execute(botDo.sendMessageToUser(message, "Введи описание квартиры(дома)"));
                    user.setCounter(++counter);
                    userBase.saveTheUpdate(user);
                    break;
                //ВВОД ПЕРВОЙ ИНФЫ
                case 1:
                    rentalAd = botFill.fillTheDescription(message);
                    execute(botDo.sendMessageToUser(message, "не забывай писать /post в начале"));
                    execute(botDo.sendMessageToUser(message, "Введи контакты для связи"));
                    user.setCounter(++counter);
                    user.setRentalAd(rentalAd);
                    userBase.saveTheUpdate(user);
                    break;
                case 2:
                    rentalAd = botFill.fillTheContacts(message, rentalAd);
                    execute(botDo.sendMessageToUser(message, "не забывай писать /post в начале"));
                    execute(botDo.sendMessageToUser(message, "Введи цену в Шекелях"));
                    user.setCounter(++counter);
                    user.setRentalAd(rentalAd);
                    userBase.saveTheUpdate(user);
                    break;
                case 3:
                    rentalAd = botFill.fillThePrice(message, rentalAd);
                    execute(botDo.sendMessageToUser(message, "не забывай писать /post в начале"));
                    execute(botDo.sendMessageToUser(message, "Введи адресс"));
                    user.setCounter(++counter);
                    user.setRentalAd(rentalAd);
                    userBase.saveTheUpdate(user);
                    break;
                case 4:
                    rentalAd = botFill.fillTheAddress(message, rentalAd);
                    execute(botDo.sendMessageToUser(message, "не забывай писать /post в начале"));
                    execute(botDo.sendMessageToUser(message, "Введи район в формате north, south, center"));
                    user.setCounter(++counter);
                    user.setRentalAd(rentalAd);
                    userBase.saveTheUpdate(user);
                    break;
                case 5:
                    rentalAd = botFill.fillTheArea(message, rentalAd);
                    execute(botDo.sendMessageToUser(message, "не забывай писать /post в начале"));
                    execute(botDo.sendMessageToUser(message, "Введи название города"));
                    user.setCounter(++counter);
                    user.setRentalAd(rentalAd);
                    userBase.saveTheUpdate(user);
                    break;
                case 6:
                    rentalAd = botFill.fillTheCityName(message, rentalAd);
                    execute(botDo.sendMessageToUser(message, "Ну вот и умничка, а ты переживал"));
                    dataBase.createRent(rentalAd);
                    user.setCounter(0);
                    user.setRentalAd(new RentObjectDto());
                    userBase.saveTheUpdate(user);
                    break;
                default:
                    user.setCounter(0);
                    user.setRentalAd(new RentObjectDto());
                    execute(botDo.sendMessageToUser(message, "Мы сбросили твое обьявление, начни сначала"));
                    userBase.saveTheUpdate(user);
                    break;
            }
        } catch (TelegramApiException | NullPointerException e) {
            user.setCounter(0);
            userBase.saveTheUpdate(user);
            e.printStackTrace();
        }
    }

    //ПРОВЕРКА ЗАПОЛНЕНИЯ БАЗЫ ОБЬЯВЛЕНИЙ
    //TODO Добавить проверку в 3,10,30 и 100 потоков
    private void TEST_FILL_THE_BASE(Message message) {
        List<String> paths = new ArrayList<>();
        paths.add("ФОТО ОБЪЕКТА ИЛИ ФОТОГРАФИИ, КОЛЛАЖ");

        for (int i = 1; i < 11; i++) {
            RentObjectDto objectDto = new RentObjectDto();
            objectDto.setId(advId.getAndIncrement());
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


