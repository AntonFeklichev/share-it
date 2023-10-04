package item;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListOfItemsDto {

    private Long id;
    private String name;
    private String description;
    private Boolean available;

}
