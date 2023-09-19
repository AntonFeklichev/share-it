package anton.myshareit.user.service;

import anton.myshareit.exceptions.UserNotFoundException;
import anton.myshareit.user.dto.UserDto;
import anton.myshareit.user.dtoMappers.UserMapper;
import anton.myshareit.user.entity.User;
import anton.myshareit.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public abstract class UserServiceTest<T extends UserService> {

    User user;
    UserDto userDto;

    @Mock
    UserRepository userRepository;


    T userService;

    abstract T getUserService();

    @BeforeEach
    public void doSetup() {
        userService = getUserService();

        user = User.builder()
                .id(1L)
                .name("Maks")
                .email("Maks@yandex.ru")
                .build();
        userDto = UserDto.builder()
                .id(1l)
                .name("Maks")
                .email("Maks@yandex.ru")
                .build();

    }

    @Test
    public void testAddUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDto resultDto = userService.addUser(this.userDto);
        assertEquals(userDto, resultDto);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testFindUserById() {
        when(userRepository.findById(userDto.getId()))
                .thenReturn(Optional.ofNullable(user));
        UserDto resultDto = userService.findUserById(user.getId());
        assertEquals(userDto, resultDto);
    }

    @Test
    public void testUpdateUser() throws JsonProcessingException {
        when(userRepository.findById(userDto.getId()))
                .thenReturn(Optional.ofNullable(user));
        String stringUserDto = new ObjectMapper().writeValueAsString(userDto);

        UserDto resultDto = userService.updateUser(stringUserDto, user.getId());

        assertEquals(userDto, resultDto);
    }

    @Test
    public void testGetAllUsers() {

        List<User> users = Stream.of(user).collect(Collectors.toList());

        when(userRepository.findAll()).thenReturn(users);

        List<UserDto> userDtoList = users.stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());

        List<UserDto> userDtoListInService = userService.getAllUsers();

        assertEquals(userDtoList, userDtoListInService);
    }

    @Test
    public void testDeleteUser(){
        when(userRepository.existsById(anyLong()))
                .thenThrow(new UserNotFoundException("User not found"));

        assertThrows(UserNotFoundException.class,() -> userService.deleteUser(2L));

    }

}
