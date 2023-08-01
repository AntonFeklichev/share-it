package anton.myshareit.user.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class User {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "user_id")
    private Long id;

@Column(name = "name")
    private String name;
@Column(name = "email")
    private String email;
}
