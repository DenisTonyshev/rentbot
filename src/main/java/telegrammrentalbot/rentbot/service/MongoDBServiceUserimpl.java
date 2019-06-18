package telegrammrentalbot.rentbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import telegrammrentalbot.rentbot.dto.UserDto;
import telegrammrentalbot.rentbot.repo.MongoDBUserRepo;

@Service
public class MongoDBServiceUserimpl implements IMongoDBUserService {

    @Autowired
    MongoDBUserRepo mongoDBUserRepo;

    @Override
    public boolean addUser(UserDto user) {
        return true;
    }

    @Override
    public boolean removeUser(Long userId) {
        return true;
    }
}
