package anton.myshareit.item.entity;

import anton.myshareit.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    @Column(name = "comment_text")
    private String text;
    @JoinColumn(name = "comment_item_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Item item;
    @JoinColumn(name = "comment_author_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User author;
    @Column(name = "created")
    private LocalDateTime created;

}
