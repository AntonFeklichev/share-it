package anton.myshareit.booking.dtoMappers;

import anton.myshareit.booking.controller.BookingController;
import anton.myshareit.booking.dto.BookingDto;
import anton.myshareit.booking.dto.CreateBookingDto;
import anton.myshareit.booking.entity.Booking;
import anton.myshareit.item.dtoMappers.ItemMapper;
import anton.myshareit.user.dtoMappers.UserMapper;

public class BookingMapper {

    public static BookingDto toDto(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .item(ItemMapper.toItemDto(booking.getItem()))
                .booker(UserMapper.toUserDto(booking.getBooker()))
                .bookerId(booking.getBooker().getId())
                .status(booking.getStatus())
                .build();
    }

    public static Booking toBooking(BookingDto bookingDto) {
        return Booking.builder()
                .id(bookingDto.getId())
                .start(bookingDto.getStart())
                .end(bookingDto.getEnd())
                .item(ItemMapper.toItem(bookingDto.getItem()))
                .booker(bookingDto.getBooker() == null ? null : UserMapper.toUser(bookingDto.getBooker()))
                .status(bookingDto.getStatus())
                .build();
    }

    public static Booking toBooking(CreateBookingDto createBookingDto) {
    return Booking.builder()
            .start(createBookingDto.start())
            .end(createBookingDto.end())
            .build();
    }
}
