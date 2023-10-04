package item;

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
        Boolean available,
        Long requestId) {

    public Boolean isAvailable() {
        return available;
    }
}


