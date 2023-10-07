package anton.myshareit.user.service;

import anton.myshareit.exceptions.AuthenticationFailedException;
import anton.myshareit.exceptions.UserAlreadyExistException;
import anton.myshareit.exceptions.UserNotFoundException;

import anton.myshareit.user.dtoMappers.UserMapper;
import anton.myshareit.user.entity.User;
import anton.myshareit.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import user.UserDto;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto addUser(UserDto userDto) {

        User user = UserMapper.toUser(userDto);
        //if (!userRepository.existsByEmail(userDto.getEmail())) {
          User savedUser = userRepository.save(user);
        //} else {
            //throw new UserAlreadyExistException("User already exist");
        //}
        return UserMapper.toUserDto(savedUser);
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
            User updatedUser = new ObjectMapper().readerForUpdating(oldUser)
                    .readValue(userDto);
            userRepository.save(updatedUser);
            return UserMapper.toUserDto(updatedUser);
        } catch (JsonProcessingException ex) {
            throw new AuthenticationFailedException("Invalid json");
        }
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }


}
