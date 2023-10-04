package anton.myshareit.booking.entity;

import anton.myshareit.item.entity.Item;
import anton.myshareit.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "booking")
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long id;
    @Column(name = "start_date")
    private LocalDateTime start;
    @Column(name = "end_date")
    private LocalDateTime end;
    @JoinColumn(name = "item_id")
    @ManyToOne(targetEntity = Item.class,fetch = FetchType.EAGER)
    private Item item;
    @JoinColumn(name = "booker_id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User booker;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
