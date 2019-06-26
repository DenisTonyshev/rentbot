package telegrammrentalbot.rentbot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    @Id
    private long id;
    private String name;
    private boolean isBot;
    private String lastName;
    private String userName;
    private String languageCode;
//TODO МОЖЕТ ОСТАВИТЬ НЕ ЛИСТ А МАП, ДЛЯ ПОИСКА ОБЯВЫ, А ТО ЧЕТ ЗАШКВАР ПОТОМ КАК ПРОВЕРИШЬ КАКАЯ ОБЯВА КОСЯЧНАЯ.
    private RentObjectDto rentalAd;
    private int counter;
}
