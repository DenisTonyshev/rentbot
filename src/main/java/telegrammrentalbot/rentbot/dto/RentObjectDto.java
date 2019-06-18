package telegrammrentalbot.rentbot.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

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
    private List<String> photo;
    private Date postDate;
    private double price;
    private boolean isActive;
    private String address;
    private String area;
}
