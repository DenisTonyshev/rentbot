package telegrammrentalbot.rentbot.service;

import telegrammrentalbot.rentbot.dto.UserDto;

import java.util.List;

public interface IMongoDBUserService {
    void addUser(UserDto user);
    void removeUser(Long userId);
    List<UserDto> allUsers();
    UserDto getUserById(long id);
    void saveTheUpdate(UserDto user);

}
