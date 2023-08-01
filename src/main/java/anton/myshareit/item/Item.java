package anton.myshareit.item;

import anton.myshareit.request.ItemRequest;
import anton.myshareit.user.entity.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Item {

    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private User owner;
    private ItemRequest request;

    public Boolean isAvailable() {
        return available;
    }
}
