package telegrammrentalbot.rentbot.botAbilities;
//      return new RentObjectDto(clientId.getAndIncrement(),
//      id,
//      textFromUser.get(0).toLowerCase(),
//      textFromUser.get(1),LocalDate.now(),
//      Double.parseDouble(textFromUser.get(2))
//      ,true,
//      textFromUser.get(3).toLowerCase()
//      ,textFromUser.get(4),
//      textFromUser.get(5),
//      null);

import org.telegram.telegrambots.meta.api.objects.*;
import telegrammrentalbot.rentbot.dto.*;

//@Id
//private Long id;
//private Integer userId;
//private String description;
//private String contacts;
//private LocalDate postDate;
//private double price;
//private boolean isActive;
//private String address;
//private String area;
//private String cityName;
//private List<String> photo;
public interface IBotFillTheRentAD {
    RentObjectDto fillTheDescription(Message msg);
    RentObjectDto fillTheContacts(Message msg, RentObjectDto user);
    RentObjectDto fillThePrice(Message msg, RentObjectDto user);
    RentObjectDto fillTheAddress(Message msg, RentObjectDto user);
    RentObjectDto fillTheArea(Message msg, RentObjectDto user);
    RentObjectDto fillTheCityName(Message msg, RentObjectDto user);
}
