package anton.myshareit.item.dtoMappers;

import anton.myshareit.item.entity.Item;
import item.ListOfItemsDto;

public class ListOfItemsDtoMapper {

    public static ListOfItemsDto toDto(Item item) {

        return ListOfItemsDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }
}
