package anton.myshareit.user.service;

import anton.myshareit.user.dto.UserDto;

public interface UserService {

    UserDto addUser(UserDto userDto);

    UserDto findUserById(Long userId);


    UserDto updateUser(String userDto, Long userId);

    Boolean deleteUser(Long userId);

}
