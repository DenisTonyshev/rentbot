package telegrammrentalbot.rentbot.botAbilities;

import org.telegram.telegrambots.meta.api.objects.Message;
import telegrammrentalbot.rentbot.dto.RentObjectDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static telegrammrentalbot.rentbot.constants.consts.*;

public class FillTheRentForm implements IBotFillTheRentAD {

    @Override
    public RentObjectDto fillTheDescription(Message msg) {
        RentObjectDto rentalAd = new RentObjectDto();
        rentalAd.setId((long)ThreadLocalRandom.current().ints(100000, 999999).findFirst().getAsInt());
        rentalAd.setActive(false);
        rentalAd.setPostDate(LocalDate.now());
        //ВЫНЕСТИ ОТДЕЛЬНО ФОТО
        rentalAd.setPhoto(new ArrayList<>());

        rentalAd.setUserId(msg.getFrom().getId());
        String text = msg.getText().trim();
        String description = text.substring(5).trim();
        if(description.equals("")){
            return null;
        }
        System.out.println(description);
        rentalAd.setDescription(description);
        return rentalAd;
    }

    @Override
    public RentObjectDto fillTheContacts(Message msg, RentObjectDto rentalAd) {
        String text = msg.getText().trim();
        String contacts = text.substring(5).trim();
        if(contacts.equals("")){
            return null;
        }
        rentalAd.setContacts(contacts);
        return rentalAd;
    }

    @Override
    public RentObjectDto fillThePrice(Message msg, RentObjectDto rentalAd) {
        String text = msg.getText().trim();
        String price = text.substring(5).trim();
        if(price.equals("")){
            return null;
        }
        double price1 = 0;
        try {
            price1 = Double.parseDouble(price);
            rentalAd.setPrice(price1);
        } catch (NumberFormatException e) {
            rentalAd.setPrice(price1);
        }
        return rentalAd;
    }

    @Override
    public RentObjectDto fillTheAddress(Message msg, RentObjectDto rentalAd) {
        String text = msg.getText().trim();
        String address = text.substring(5).trim();
        if(address.equals("")){
            return null;
        }
        rentalAd.setAddress(address);
        return rentalAd;
    }

    @Override
    public RentObjectDto fillTheArea(Message msg, RentObjectDto rentalAd) {
        String text = msg.getText().trim();
        String area = text.substring(5).trim();
        if(area.equals("")){
            return null;
        }
        rentalAd.setArea(area);
        return rentalAd;
    }

    @Override
    public RentObjectDto fillTheCityName(Message msg, RentObjectDto rentalAd) {
        String text = msg.getText().trim();
        String citName = text.substring(5).trim();
        if(citName.equals("")){
            return null;
        }
        rentalAd.setCityName(citName);
        rentalAd.setActive(true);
        return rentalAd;
    }
}
