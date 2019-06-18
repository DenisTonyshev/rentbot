package telegrammrentalbot.rentbot.service;

import telegrammrentalbot.rentbot.dto.UserDto;

public interface IMongoDBUserService {
    boolean addUser(UserDto user);
    boolean removeUser(Long userId);

}
