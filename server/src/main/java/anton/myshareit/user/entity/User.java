package anton.myshareit.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users",
        uniqueConstraints = @UniqueConstraint(
                name = "users_email_unique",
                columnNames = "email"
        )
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",
            nullable = false)
    private Long id;

    @Column(name = "name",
            nullable = false)
    private String name;

    @Column(name = "email",
            nullable = false,
            unique = true)
    private String email;
}
