package item.comment;

import anton.myshareit.item.dto.ItemDto;
import anton.myshareit.user.dto.UserDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    @NotBlank
    private String text;
    @NotNull
    private ItemDto item;
    @NotNull
    private String authorName;
    private LocalDateTime created;

}