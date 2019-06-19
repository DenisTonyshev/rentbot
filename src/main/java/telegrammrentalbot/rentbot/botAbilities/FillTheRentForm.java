package telegrammrentalbot.rentbot.botAbilities;

import org.telegram.telegrambots.meta.api.objects.Message;
import telegrammrentalbot.rentbot.dto.RentObjectDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static telegrammrentalbot.rentbot.constants.consts.*;

public class FillTheRentForm implements IBotFillTheRentAD {

    @Override
    public RentObjectDto fillTheDescription(Message msg) {
        RentObjectDto user = new RentObjectDto();
        user.setId(clientId.getAndIncrement());
        user.setActive(true);
        user.setPostDate(LocalDate.now());
        //ВЫНЕСТИ ОТДЕЛЬНО ФОТО
        user.setPhoto(new ArrayList<>());

        Integer userid = msg.getFrom().getId();
        user.setUserId(userid);
        String text = msg.getText();
        String description = text.substring(6).trim();
        System.out.println(description);
        user.setDescription(description);
        return user;
    }

    @Override
    public RentObjectDto fillTheContacts(Message msg, RentObjectDto user) {
        String text = msg.getText();
        String contacts = text.substring(6).trim();
        user.setContacts(contacts);
        return user;
    }

    @Override
    public RentObjectDto fillThePrice(Message msg, RentObjectDto user) {
        String text = msg.getText();
        String price = text.substring(6).trim();
        double price1 = 0;
        try {
            price1 = Double.parseDouble(price);
            user.setPrice(price1);
        } catch (NumberFormatException e) {
            user.setPrice(price1);
        }
        return user;
    }

    @Override
    public RentObjectDto fillTheAddress(Message msg, RentObjectDto user) {
        String text = msg.getText();
        String address = text.substring(6).trim();
        user.setAddress(address);
        return user;
    }

    @Override
    public RentObjectDto fillTheArea(Message msg, RentObjectDto user) {
        String text = msg.getText();
        String area = text.substring(6).trim();
        user.setArea(area);
        return user;
    }

    @Override
    public RentObjectDto fillTheCityName(Message msg, RentObjectDto user) {
        String text = msg.getText();
        String citName = text.substring(6).trim();
        user.setCityName(citName);
        return user;
    }
}
