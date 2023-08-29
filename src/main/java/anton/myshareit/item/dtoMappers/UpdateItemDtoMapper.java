package anton.myshareit.item.dtoMappers;

import anton.myshareit.item.dto.UpdateItemDto;
import anton.myshareit.item.entity.Item;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateItemDtoMapper {

    public static UpdateItemDto toUpdateItemDto(Item item) {

        return UpdateItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }

}
