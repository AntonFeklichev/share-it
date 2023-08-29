package anton.myshareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
public record CreateItemDto(
        @NotBlank
        String name,
        @NotBlank
        String description,
        @NotNull
        Boolean available) {

    public Boolean isAvailable() {
        return available;
    }
}


