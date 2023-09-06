package anton.myshareit.item.dto;

import lombok.Builder;

@Builder
public record CreateItemResponseDto(
        Long id,
        String name,
        String description,
        Boolean available,
        Long requestId) {
    public Boolean isAvailable() {
        return available;
    }
}

