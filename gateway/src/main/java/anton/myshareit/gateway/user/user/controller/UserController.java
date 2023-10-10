package anton.myshareit.gateway.user.user.controller;


import anton.myshareit.gateway.user.user.client.UserClient;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import user.UserDto;


@RestController
@RequestMapping(path = "/users")
@Slf4j
@Validated
public class UserController {

    private final UserClient userClient;

    @Autowired
    public UserController(UserClient userClient) {
        this.userClient = userClient;
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserDto userDto) {
        log.info("Creating User name{}, id{}", userDto.getName(), userDto.getId());
        return userClient.addUser(userDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> findUserById(@PathVariable
                                               Long userId) {
        log.info("Finding User by id{}", userId);
        return userClient.findUserById(userId);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody
                                             String userDto,
                                             @PathVariable
                                             Long userId) {
        log.info("Updating User by id{}", userId);
        return userClient.updateUser(userDto, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        log.info("Getting all users");
        return userClient.getAllUsers();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable
                                             Long userId) {
        log.info("Deleting User by id{}", userId);
        userClient.deleteUser(userId);

        return ResponseEntity.noContent().build();

    }
}
