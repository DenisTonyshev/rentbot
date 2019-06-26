package telegrammrentalbot.rentbot.botAbilities;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import telegrammrentalbot.rentbot.dto.RentObjectDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static telegrammrentalbot.rentbot.constants.consts.*;

public class FillTheRentForm implements IBotFillTheRentAD {

    @Override
    public RentObjectDto fillTheDescription(Message msg, RentObjectDto rentalAd) {
        rentalAd.setId((long) ThreadLocalRandom.current().ints(100000, 999999).findFirst().getAsInt());
        rentalAd.setPostDate(LocalDate.now());
        //ВЫНЕСТИ ОТДЕЛЬНО ФОТО
        rentalAd.setPhoto(new ArrayList<>());
        rentalAd.setUserId(msg.getFrom().getId());
        String text = msg.getText().trim();
        String description = text.substring(5).trim();
        if (description.equals("")) {
            rentalAd.setContacts("NO DESCRIPTION");
        } else {
            rentalAd.setDescription(description);
        }
        return rentalAd;
    }

    @Override
    public RentObjectDto fillTheContacts(Message msg, RentObjectDto rentalAd) {
        String text = msg.getText().trim();
        String contacts = text.substring(5).trim();
        if (contacts.equals("")) {
            rentalAd.setContacts("NO CONTACTS");
        } else {
            rentalAd.setContacts(contacts);
        }
        return rentalAd;
    }

    @Override
    public RentObjectDto fillThePrice(Message msg, RentObjectDto rentalAd) {
        double price1 = 0;
        String text = msg.getText().trim();
        String price = text.substring(5).trim();
        if (price.equals("")) {
            rentalAd.setPrice(0);
        } else {
            try {
                price1 = Double.parseDouble(price);
                rentalAd.setPrice(price1);
            } catch (NumberFormatException e) {
                rentalAd.setPrice(price1);
            }
        }
        return rentalAd;
    }

    @Override
    public RentObjectDto fillTheAddress(Message msg, RentObjectDto rentalAd) {
        String text = msg.getText().trim();
        String address = text.substring(5).trim();
        if (address.equals("")) {
            rentalAd.setAddress("NO ADDRESS");
        } else {
            rentalAd.setAddress(address);
        }
        return rentalAd;
    }

    @Override
    public RentObjectDto fillTheArea(Message msg, RentObjectDto rentalAd) {
        String text = msg.getText().trim();
        String area = text.substring(5).trim();
        if (area.equals("")) {
            rentalAd.setAddress("NO AREA");
        } else {
            rentalAd.setArea(area.toUpperCase());
        }
        return rentalAd;
    }

    @Override
    public RentObjectDto fillTheCityName(Message msg, RentObjectDto rentalAd) {
        String text = msg.getText().trim();
        String citName = text.substring(5).trim();
        if (citName.equals("")) {
            rentalAd.setAddress("NO CITY_NAME");
        } else {
            rentalAd.setCityName(citName.toUpperCase());
        }
        return rentalAd;
    }

    @Override
    public RentObjectDto fillThePhoto(Message msg, RentObjectDto rentalAd) {
        if (rentalAd.getPhoto() == null) {
            rentalAd.setPhoto(new ArrayList<>());
        }
        rentalAd.getPhoto().add(msg.getPhoto().get(0).getFileId());
        rentalAd.setActive(true);
        return rentalAd;
    }

}
