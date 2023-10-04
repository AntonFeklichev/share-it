package item;

import anton.myshareit.booking.dto.BookingDto;
import anton.myshareit.item.dto.comment.CommentDto;
import anton.myshareit.request.dto.GetRequestDto;
import anton.myshareit.request.entity.ItemRequest;
import anton.myshareit.user.dto.UserDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ItemDto {

    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Boolean available;
    private UserDto owner;
    private GetRequestDto request;

    private BookingDto lastBooking;
    private BookingDto nextBooking;
    private List<CommentDto> comments = new ArrayList<>();

    public Boolean isAvailable() {
        return available;
    }
}
