package telegrammrentalbot.Dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode

public class RentObjectDto {
    @Id
    private Long id;
    private Long userId;
    private String description;
    private String contacts;
    private String photo;
    private Date postDate;
    private double price;
    private boolean inActive;
    private String address;
    private String area;
}
