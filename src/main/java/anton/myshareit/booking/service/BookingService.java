package anton.myshareit.booking.service;

import anton.myshareit.booking.controller.StateEnum;
import anton.myshareit.booking.dto.BookingDto;
import anton.myshareit.booking.dto.CreateBookingDto;
import anton.myshareit.booking.entity.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface BookingService {

    BookingDto addBooking(CreateBookingDto createBookingDto, Long userId);
    BookingDto approveBooking(Long bookingId, Boolean approved, Long userId);
    BookingDto getBookingStatus(Long bookingId, Long userId);
    Page<BookingDto> getBookingList(BookingStatus state, Long userId, Pageable pageRequest);
    Page<BookingDto> getBookingListByOwner(BookingStatus state, Long userId, Pageable pageRequest);
    BookingDto getLastBookingByItem(Long itemId);
    BookingDto getNextBookingByItem(Long itemId);
}
