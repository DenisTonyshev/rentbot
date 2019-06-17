package telegrammrentalbot.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private long id;
    private String name;
    private boolean isBot;
    private String lastname;
    private String userName;
    private String languageCode;
}
