package anton.myshareit.item.entity;

import anton.myshareit.booking.entity.Booking;
import anton.myshareit.request.entity.ItemRequest;
import anton.myshareit.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "items")

public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "available")
    private Boolean available;
    @JoinColumn(name = "owner_id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User owner;
    @ManyToOne()
    private ItemRequest request;
    @OneToMany(mappedBy = "item")
    @Builder.Default
    private List<Booking> bookings = new ArrayList<>();
    @OneToMany(mappedBy = "item")
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    public Boolean isAvailable() {
        return available;
    }

    public void setRequest(ItemRequest itemRequest) {
        this.request = itemRequest;
        itemRequest.getItems().add(this);
    }

    public void addBooking(Booking booking){
        bookings.add(booking);
        booking.setItem(this);
    }
}
