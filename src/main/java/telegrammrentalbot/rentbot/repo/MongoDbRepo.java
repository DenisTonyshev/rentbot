package telegrammrentalbot.rentbot.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import telegrammrentalbot.rentbot.dto.RentObjectDto;

import java.util.List;

public interface MongoDbRepo extends MongoRepository<RentObjectDto,Long> {
    List<RentObjectDto> findByArea(String area);
    List<RentObjectDto> findByUserId(Long userid);
}
