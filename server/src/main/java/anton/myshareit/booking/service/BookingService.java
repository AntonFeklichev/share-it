package anton.myshareit.booking.service;

import booking.BookingDto;
import booking.BookingStatus;
import booking.CreateBookingDto;
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
