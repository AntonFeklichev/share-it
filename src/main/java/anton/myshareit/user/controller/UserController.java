package anton.myshareit.user.controller;

import anton.myshareit.user.dto.UserDto;
import anton.myshareit.user.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
@Slf4j
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @GetMapping("/{userId}")
    public UserDto findUserById(@PathVariable
                                Long userId) {
        return userService.findUserById(userId);
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@RequestBody
                              String userDto,
                              @PathVariable
                              Long userId) {

        return userService.updateUser(userDto, userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable
                                             Long userId) {
        Boolean isDelete = userService.deleteUser(userId);
        if (isDelete) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
