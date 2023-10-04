package anton.myshareit.user.dtoMappers;


import user.dto.UserDto;
import anton.myshareit.user.entity.User;

public class UserMapper {

    public static UserDto toUserDto (User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static User toUser (UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }
}
