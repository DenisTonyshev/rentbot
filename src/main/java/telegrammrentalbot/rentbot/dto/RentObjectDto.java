package telegrammrentalbot.rentbot.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.*;
import java.util.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RentObjectDto {


    private Integer userId;
    private String description;
    private String contacts;
    @Id
    private LocalDate postDate;
    private double price;
    private boolean isActive;
    private String address;
    private String area;
    private String cityName;
    private List<String> photo;
}
