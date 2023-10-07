package anton.myshareit.booking.service;


import anton.myshareit.booking.dtoMappers.BookingMapper;
import anton.myshareit.booking.entity.Booking;
import anton.myshareit.booking.repository.BookingRepository;
import anton.myshareit.exceptions.*;
import anton.myshareit.item.entity.Item;
import anton.myshareit.item.repository.ItemRepository;
import anton.myshareit.user.entity.User;
import anton.myshareit.user.repository.UserRepository;
import booking.BookingDto;
import booking.BookingStatus;
import booking.CreateBookingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public BookingDto addBooking(CreateBookingDto createBookingDto, Long userId) {

        User booker = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Item item = itemRepository.findById(createBookingDto.itemId())
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));
        if (Objects.equals(booker.getId(), item.getOwner().getId())) {
            throw new ItemNotFoundException("You cant book your items");
        }
        if (!item.isAvailable()) {
            throw new ItemUnavailableException("Item is unavailable");
        }

        List<Booking> bookingList = item.getBookings();

        bookingCheck(createBookingDto, bookingList);

        Booking booking = BookingMapper.toBooking(createBookingDto);
        booking.setStatus(BookingStatus.WAITING);
        booking.setItem(item);
        booking.setBooker(booker);
        Booking savedBooking = bookingRepository.save(booking);

        return BookingMapper.toDto(savedBooking);
    }

    private static void bookingCheck(CreateBookingDto createBookingDto, List<Booking> bookingList) {
        if (!createBookingDto.start().isBefore(createBookingDto.end())) {
            throw new InvalidBookingTimeException("Wrong start or end times");
        }

        bookingList.forEach(booking -> {
            if (booking.getEnd().isBefore(createBookingDto.end())
                    && booking.getEnd().isAfter(createBookingDto.start())) {
                throw new ItemAlreadyBookingException("Item already booking");
            }
            if (booking.getStart().isAfter(createBookingDto.start())
                    && booking.getEnd().isBefore(createBookingDto.end())) {
                throw new ItemAlreadyBookingException("Item already booking");
            }
            if (booking.getStart().isBefore(createBookingDto.start())
                    && booking.getEnd().isAfter(createBookingDto.end())) {
                throw new ItemAlreadyBookingException("Item already booking");
            }
            if (booking.getStart().isAfter(createBookingDto.start())
                    && booking.getStart().isBefore(createBookingDto.end())) {
                throw new ItemAlreadyBookingException("Item already booking");
            }

        });
    }

    @Override
    @Transactional
    public BookingDto approveBooking(Long bookingId, Boolean approved, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
        Item item = booking.getItem();

        if (!Objects.equals(booking.getStatus(), BookingStatus.WAITING)) {
            throw new BadRequestExceptions("No bookings in waiting status");
        }
        if (Objects.equals(item.getOwner().getId(), userId)) {
            booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        } else {
            throw new UserNotFoundException("You are not owner of items");
        }
        bookingRepository.save(booking);
        return BookingMapper.toDto(booking);
    }

    @Override
    @Transactional
    public BookingDto getBookingStatus(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
        Item item = booking.getItem();

        if (Objects.equals(booking.getBooker().getId(), userId)
                || Objects.equals(item.getOwner().getId(), userId)) {
            return BookingMapper.toDto(booking);
        } else {
            throw new UserNotFoundException("You have not items or bookings");
        }
    }

    @Override
    @Transactional
    public Page<BookingDto> getBookingList(BookingStatus state, Long userId, Pageable pageRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        LocalDateTime now = LocalDateTime.now();

        return switch (state) {
            case FUTURE -> bookingRepository.getFutureBookingList(now, userId, pageRequest)
                    .map(BookingMapper::toDto);

            case PAST -> bookingRepository.getPastBookingList(now, userId, pageRequest)
                    .map(BookingMapper::toDto);

            case CURRENT -> bookingRepository.getCurrentBookingList(now, userId, pageRequest)
                    .map(BookingMapper::toDto);
            default -> bookingRepository.getBookingList(state, userId, pageRequest)
                    .map(BookingMapper::toDto);
        };

    }

    @Override
    @Transactional
    public Page<BookingDto> getBookingListByOwner(BookingStatus state, Long userId, Pageable pageRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        LocalDateTime now = LocalDateTime.now();

        return switch (state) {
            case FUTURE -> bookingRepository.getFutureBookingListByOwnerWhitState(now, userId, pageRequest)
                    .map(BookingMapper::toDto);
            case PAST -> bookingRepository.getPastBookingListByOwnerWhitState(now, userId, pageRequest)
                    .map(BookingMapper::toDto);
            case CURRENT -> bookingRepository.getPastCurrentListByOwnerWhitState(now, userId, pageRequest)
                    .map(BookingMapper::toDto);
            case ALL -> bookingRepository.getBookingListByOwner(userId, pageRequest)
                    .map(BookingMapper::toDto);
            default -> bookingRepository.getBookingListByOwnerWhitState(state, userId, pageRequest)
                    .map(BookingMapper::toDto);
        };
    }

    @Override
    public BookingDto getLastBookingByItem(Long itemId) {
        LocalDateTime now = LocalDateTime.now();

        return bookingRepository.getLastBookingByItem(itemId, now)
                .stream()
                .findFirst()
                .map(BookingMapper::toDto)
                .orElse(null);
    }

    @Override
    public BookingDto getNextBookingByItem(Long itemId) {
        LocalDateTime now = LocalDateTime.now();
        return bookingRepository.getNextBookingByItem(itemId, now)
                .stream()
                .findFirst()
                .map(BookingMapper::toDto)
                .orElse(null);
    }
}

