package anton.myshareit.item.dtoMappers;

import anton.myshareit.item.entity.Item;
import anton.myshareit.item.dto.ItemDto;

public class ItemMapper {

    public static ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.isAvailable(),
                item.getOwner(),
                item.getRequest() //!= null ? item.getRequest().getId() : null
        );
    }


}
