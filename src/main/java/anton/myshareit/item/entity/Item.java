package anton.myshareit.item.entity;

import anton.myshareit.booking.entity.Booking;
import anton.myshareit.request.ItemRequest;
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
    @Transient
    private ItemRequest request;
    @OneToMany(mappedBy = "item")
    private List<Booking> bookings;
    @OneToMany(mappedBy = "item")
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    public Boolean isAvailable() {
        return available;
    }
}
