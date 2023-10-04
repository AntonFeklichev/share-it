package anton.myshareit.booking.dto;

import anton.myshareit.booking.entity.BookingStatus;
import item.ItemDto;
import anton.myshareit.item.entity.Item;
import anton.myshareit.user.dto.UserDto;
import anton.myshareit.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {

    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private ItemDto item;
    private UserDto booker;
    private Long bookerId;
    private BookingStatus status;

}
