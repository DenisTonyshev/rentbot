package telegrammrentalbot.rentbot.service;

import telegrammrentalbot.rentbot.dto.RentObjectDto;

import java.util.List;

public interface IMongoDBService {
    //CRUD
    boolean createRent(RentObjectDto rentObjectDto);
    boolean delleteRent(Long id);
    List<RentObjectDto> allAreaRents(String area);
    List<RentObjectDto> allUserRents(long userId);
    List<RentObjectDto> allCityRents(String cityName);
}
