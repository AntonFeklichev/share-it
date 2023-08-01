package anton.myshareit.user.service;

import anton.myshareit.exceptions.UserNotFoundException;
import anton.myshareit.user.dto.UserDto;
import anton.myshareit.user.dtoMappers.UserMapper;
import anton.myshareit.user.entity.User;
import anton.myshareit.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.stereotype.Service;


@Service
@Data
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

@Override
    public UserDto addUser(UserDto userDto) {

        User user = UserMapper.toUser(userDto);
        userRepository.save(user);
        return UserMapper.toUserDto(user);
    }
@Override
    public UserDto findUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserMapper.toUserDto(user);
    }

@Override
    public UserDto updateUser(String userDto, Long userId) {
        User oldUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        try {
            User updatedUser = new ObjectMapper().readerForUpdating(oldUser).readValue(userDto);
            userRepository.save(updatedUser);
            return UserMapper.toUserDto(updatedUser);
        } catch (JsonProcessingException ex) {
            ex.getMessage();
            return null;
        }
    }
@Override
    public Boolean deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        } else {
            throw new UserNotFoundException("User not found");
        }
    }


}
