package anton.myshareit.user.controller;


import anton.myshareit.user.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user.UserDto;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto addUser(@Valid @RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @GetMapping("/{userId}")
    public UserDto findUserById(@PathVariable
                                Long userId) {
        return userService.findUserById(userId);
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@Valid @RequestBody
                              String userDto,
                              @PathVariable
                              Long userId) {
        return userService.updateUser(userDto, userId);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable
                                             Long userId) {
        userService.deleteUser(userId);

        return ResponseEntity.noContent().build();

    }
}
