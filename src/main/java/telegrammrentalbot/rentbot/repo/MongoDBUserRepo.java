package telegrammrentalbot.rentbot.repo;


import org.springframework.data.mongodb.repository.MongoRepository;
import telegrammrentalbot.rentbot.dto.UserDto;

public interface MongoDBUserRepo extends MongoRepository<UserDto, Long> {
}
