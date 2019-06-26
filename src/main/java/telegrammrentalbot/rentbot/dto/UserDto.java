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
    private RentObjectDto rentalAd;
    private int counter;
}
