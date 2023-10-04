package item;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemForRequestDto {

    private Long id;

    private String name;
    private String description;
    private Boolean available;

    private Long requestId;


    public Boolean isAvailable() {
        return available;
    }

}
