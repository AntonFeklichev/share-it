package request;

import item.ItemForRequestDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetRequestDto {

    private Long id;
    private String description;
    private LocalDateTime created;
    @Builder.Default
    private List<ItemForRequestDto> items = new ArrayList<>();

}
