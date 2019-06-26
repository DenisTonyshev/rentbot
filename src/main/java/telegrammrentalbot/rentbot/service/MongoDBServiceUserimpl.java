package telegrammrentalbot.rentbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import telegrammrentalbot.rentbot.dto.UserDto;
import telegrammrentalbot.rentbot.repo.MongoDBUserRepo;

import java.util.List;

@Service
public class MongoDBServiceUserimpl implements IMongoDBUserService {

    @Autowired
    MongoDBUserRepo mongoDBUserRepo;

    @Override
    public void addUser(UserDto user) {
        if (mongoDBUserRepo.existsById(user.getId())){
            return;
        }
        mongoDBUserRepo.save(user);
    }

    @Override
    public void removeUser(Long userId) {
        if (mongoDBUserRepo.existsById(userId)) {
            mongoDBUserRepo.deleteById(userId);
            return;
        }
        return;
    }

    @Override
    public List<UserDto> allUsers() {
        return mongoDBUserRepo.findAll();
    }

    @Override
    public UserDto getUserById(long id) {
        //TODO написать метод получения пользователя.
        return null;
    }

    @Override
    public void saveTheUpdate(UserDto user) {
        mongoDBUserRepo.save(user);
    }
}
