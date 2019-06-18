package telegrammrentalbot.rentbot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    @Id
    private long id;
    private String name;
    private boolean isBot;
    private String lastname;
    private String userName;
    private String languageCode;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isBot() {
        return isBot;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUserName() {
        return userName;
    }

    public String getLanguageCode() {
        return languageCode;
    }
}
