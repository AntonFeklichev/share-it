package request;

import anton.myshareit.user.dto.UserDto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RequestDto {

    private Long id;
    @NotBlank
    private String description;
    private UserDto requester;
    private LocalDateTime created;


}
