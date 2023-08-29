package anton.myshareit.booking.controller;

import anton.myshareit.booking.dto.BookingDto;
import anton.myshareit.booking.dto.CreateBookingDto;
import anton.myshareit.booking.entity.BookingStatus;
import anton.myshareit.booking.service.BookingService;
import anton.myshareit.exceptions.AuthenticationFailedException;
import anton.myshareit.exceptions.WrongStateExceptions;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static anton.myshareit.constants.Constants.DEFAULT_PAGE_SIZE;
import static anton.myshareit.constants.Constants.X_SHARER_USER_ID;

@RestController
@RequestMapping(path = "/bookings")

public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingDto addBooking(@Valid
                                 @RequestBody
                                 CreateBookingDto createBookingDto,
                                 @RequestHeader(name = X_SHARER_USER_ID)
                                 Long userId) {
        return bookingService.addBooking(createBookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto approveBooking(@PathVariable(name = "bookingId")
                                     Long bookingId,
                                     @RequestParam(name = "approved")
                                     Boolean approved,
                                     @RequestHeader(name = X_SHARER_USER_ID)
                                     Long userId) {
        return bookingService.approveBooking(bookingId, approved, userId);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBookingStatus(@PathVariable(name = "bookingId")
                                       Long bookingId,
                                       @RequestHeader(name = X_SHARER_USER_ID)
                                       Long userId) {
        return bookingService.getBookingStatus(bookingId, userId);
    }

    @GetMapping
    public List<BookingDto> getBookingList(@RequestParam(name = "state", defaultValue = "ALL")
                                           String stringState,
                                           @RequestHeader(name = X_SHARER_USER_ID)
                                           Long userId,
                                           @RequestParam(name = "pageNumber", defaultValue = "0")
                                           Integer pageNumber,
                                           @RequestParam(name = "pageSize",
                                                   defaultValue = DEFAULT_PAGE_SIZE)
                                           Integer pageSize) throws ValidationException {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("start")
                .descending());

        try {
            BookingStatus state = BookingStatus.valueOf(stringState);
            return bookingService.getBookingList(state, userId, pageRequest).getContent();

        } catch (IllegalArgumentException e) {
            throw new ValidationException("Unknown state: %s".formatted(stringState));

        }

    }

    @GetMapping("/owner")
    public List<BookingDto> getBookingListByOwner(@RequestParam(defaultValue = "ALL", name = "state")
                                                  String stateString,
                                                  @RequestHeader(name = X_SHARER_USER_ID)
                                                  Long userId,
                                                  @RequestParam(name = "pageNumber",
                                                          defaultValue = "0")
                                                  Integer pageNumber,
                                                  @RequestParam(name = "pageSize",
                                                          defaultValue = DEFAULT_PAGE_SIZE)
                                                  Integer pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("start")
                .descending());

        try {
            BookingStatus state = BookingStatus.valueOf(stateString);
            return bookingService.getBookingListByOwner(state, userId, pageRequest).getContent();
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Unknown state: %s".formatted(stateString));
        }
    }
}

