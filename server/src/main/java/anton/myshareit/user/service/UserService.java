package anton.myshareit.user.service;



import user.UserDto;

import java.util.List;

public interface UserService {

    UserDto addUser(UserDto userDto);

    UserDto findUserById(Long userId);


    UserDto updateUser(String userDto, Long userId);

    List<UserDto> getAllUsers();

    void deleteUser(Long userId);

}
