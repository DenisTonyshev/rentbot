package telegrammrentalbot.rentbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import telegrammrentalbot.rentbot.dto.RentObjectDto;
import telegrammrentalbot.rentbot.repo.MongoDbRepo;

import java.util.List;

public class MongoDBServiceImpl implements IMongoDBService {
    @Autowired
    MongoDbRepo mongoDbRepo;

    @Override
    @Transactional
    public boolean createRent(RentObjectDto rentObjectDto) {
        mongoDbRepo.save(rentObjectDto);
        return true;
    }

    @Override
    @Transactional
    public boolean delleteRent(Long id) {
        RentObjectDto rentObjectDto = mongoDbRepo.findById(id).orElse(null);
        if (rentObjectDto!=null) {
            mongoDbRepo.delete(rentObjectDto);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public List<RentObjectDto> allAreaRents(String area) {
        return mongoDbRepo.findByArea(area);
    }

    @Override
    @Transactional
    public List<RentObjectDto> allUserRents(long userId) {
        return mongoDbRepo.findByUserId(userId);
    }
}
