package anton.myshareit.booking.repository;


import anton.myshareit.booking.entity.Booking;
import booking.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b " +
            "FROM Booking b " +
            "WHERE b.booker.id = ?2 " +
            "AND (b.status = ?1 OR ?1 = 'ALL') " +
            "ORDER BY b.start desc")
    Page<Booking> getBookingList(BookingStatus state, Long userId, Pageable pageRequest);

    @Query("SELECT b " +
            "FROM Booking b " +
            "WHERE b.booker.id = ?2 " +
            "AND b.start > ?1 " +
            "ORDER BY b.start desc")
    Page<Booking> getFutureBookingList(LocalDateTime now, Long userId, Pageable pageRequest);

    @Query("SELECT b " +
            "FROM Booking b " +
            "WHERE b.booker.id = ?2 " +
            "AND b.end < ?1 " +
            "ORDER BY b.end desc")
    Page<Booking> getPastBookingList(LocalDateTime now, Long userId, Pageable pageRequest);

    @Query("SELECT b " +
            "FROM Booking b " +
            "WHERE b.booker.id = ?2 " +
            "AND b.start < ?1 " +
            "AND b.end > ?1 " +
            "ORDER BY b.start asc")
    Page<Booking> getCurrentBookingList(LocalDateTime now, Long userId, Pageable pageRequest);


    @Query("SELECT b " +
            "FROM Booking b " +
            "WHERE b.item.owner.id = ?1 " +
            "ORDER BY b.start desc")
    Page<Booking> getBookingListByOwner(Long userId, Pageable pageRequest);

    @Query("SELECT b " +
            "FROM Booking b " +
            "WHERE b.item.owner.id = ?2 " +
            "AND b.start > ?1 " +
            "ORDER BY b.start desc")
    Page<Booking> getFutureBookingListByOwnerWhitState(LocalDateTime now, Long userId, Pageable pageRequest);

    @Query("SELECT b " +
            "FROM Booking b " +
            "WHERE b.item.owner.id = ?2 " +
            "AND b.end < ?1 " +
            "ORDER BY b.end desc")
    Page<Booking> getPastBookingListByOwnerWhitState(LocalDateTime now, Long userId, Pageable pageRequest);

    @Query("SELECT b " +
            "FROM Booking b " +
            "WHERE b.item.owner.id = ?2 " +
            "AND b.start < ?1 " +
            "AND b.end > ?1 " +
            "ORDER BY b.end desc")
    Page<Booking> getPastCurrentListByOwnerWhitState(LocalDateTime now, Long userId, Pageable pageRequest);

    @Query("SELECT b " +
            "FROM Booking b " +
            "WHERE b.item.owner.id = ?2 " +
            "AND b.status = ?1 " +
            "ORDER BY b.start desc")
    Page<Booking> getBookingListByOwnerWhitState(BookingStatus state, Long userId, Pageable pageRequest);


    @Query("SELECT b " +
            "FROM Booking b " +
            "WHERE b.item.id = ?1 " +
            "AND b.start < ?2 " +
            "AND b.status = 'APPROVED' " +
            "ORDER BY b.start DESC")
    List<Booking> getLastBookingByItem(Long itemId, LocalDateTime now);
    @Query("SELECT b " +
            "FROM Booking b " +
            "WHERE b.item.id = ?1 " +
            "AND b.start > ?2 " +
            "AND b.status = 'APPROVED' " +
            "ORDER BY b.start ASC")
    List<Booking> getNextBookingByItem(Long itemId, LocalDateTime now);

    @Query("""
            SELECT count(b) > 0
            FROM Booking b
            WHERE b.booker.id = ?1
            AND b.item.id = ?2
            AND b.status = 'APPROVED'
            AND b.start < ?3
            """)
    Boolean isItemBookedByUser(Long userId, Long itemId, LocalDateTime now);

}
