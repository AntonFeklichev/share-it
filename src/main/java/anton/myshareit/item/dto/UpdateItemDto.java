package anton.myshareit.item.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateItemDto {

    private String name;
    private String description;
    private Boolean available;
}
