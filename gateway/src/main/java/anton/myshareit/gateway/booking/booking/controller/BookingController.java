package anton.myshareit.gateway.booking.booking.controller;

import anton.myshareit.gateway.booking.booking.client.BookingClient;
import booking.BookingStatus;
import booking.CreateBookingDto;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static constants.Constants.DEFAULT_PAGE_SIZE;
import static constants.Constants.X_SHARER_USER_ID;

@RestController
@RequestMapping(path = "/bookings")
@Validated
@Slf4j
public class BookingController {

    private final BookingClient bookingClient;

    @Autowired
    public BookingController(BookingClient bookingClient) {
        this.bookingClient = bookingClient;
    }

    @PostMapping
    public ResponseEntity<Object> addBooking(@Valid
                                             @RequestBody
                                             CreateBookingDto createBookingDto,
                                             @RequestHeader(name = X_SHARER_USER_ID)
                                             Long userId) {
        log.info("Add booking by userId {}", userId);
        return bookingClient.addBooking(createBookingDto, userId);
    }


    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> approveBooking(@PathVariable(name = "bookingId")
                                                 Long bookingId,
                                                 @RequestParam(name = "approved")
                                                 Boolean approved,
                                                 @RequestHeader(name = X_SHARER_USER_ID)
                                                 Long userId) {
        log.info("Approve bookingId {} by userId {} ", bookingId, userId);
        return bookingClient.approveBooking(bookingId, approved, userId);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBookingStatus(@PathVariable(name = "bookingId")
                                                   Long bookingId,
                                                   @RequestHeader(name = X_SHARER_USER_ID)
                                                   Long userId) {
        log.info("Get booking status by bookingId {}, by userId {}", bookingId, userId);
        return bookingClient.getBookingStatus(bookingId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getBookingList(@RequestParam(name = "state", defaultValue = "ALL")
                                                 String stringState,
                                                 @RequestHeader(name = X_SHARER_USER_ID)
                                                 Long userId,
                                                 @RequestParam(name = "from", defaultValue = "0")
                                                 @PositiveOrZero
                                                 Integer from,
                                                 @RequestParam(name = "size",
                                                         defaultValue = DEFAULT_PAGE_SIZE)
                                                 @Positive
                                                 Integer size) {
        log.info("Get bookingList by parameters: state = {}, from = {}, size = {} by userId",
                stringState, from, size, userId);

        try {
            BookingStatus state = BookingStatus.valueOf(stringState);
            return bookingClient.getBookingList(state, userId, from, size);

        } catch (IllegalArgumentException e) {
            throw new ValidationException("Unknown state: %s".formatted(stringState));

        }

    }


    @GetMapping("/owner")
    public ResponseEntity<Object> getBookingListByOwner(@RequestParam(defaultValue = "ALL", name = "state")
                                                        String stateString,
                                                        @RequestHeader(name = X_SHARER_USER_ID)
                                                        Long userId,
                                                        @RequestParam(name = "from",
                                                                defaultValue = "0")
                                                        @PositiveOrZero
                                                        Integer from,
                                                        @RequestParam(name = "size",
                                                                defaultValue = DEFAULT_PAGE_SIZE)
                                                        @Positive
                                                        Integer size) {
        log.info("Get bookingList by owner id {} by parameters: state = {}, from = {}, size = {}",
                userId, stateString, from, size);
        try {
            BookingStatus state = BookingStatus.valueOf(stateString);
            return bookingClient.getBookingListByOwner(state, userId, from, size);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Unknown state: %s".formatted(stateString));
        }
    }


}
