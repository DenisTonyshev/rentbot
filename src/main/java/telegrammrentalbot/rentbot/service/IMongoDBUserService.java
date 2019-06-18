package telegrammrentalbot.rentbot.service;

import telegrammrentalbot.rentbot.dto.UserDto;

public interface IMongoDBUserService {
    void addUser(UserDto user);
    void removeUser(Long userId);

}
