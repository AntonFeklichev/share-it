package anton.myshareit.item.dtoMappers;

import anton.myshareit.item.entity.Item;
import anton.myshareit.item.dto.ListOfItemsDto;

public class ListOfItemsDtoMapper {

    public static ListOfItemsDto toDto(Item item) {

        return ListOfItemsDto.builder()
                .name(item.getName())
                .description(item.getDescription())
                .build();
    }
}
