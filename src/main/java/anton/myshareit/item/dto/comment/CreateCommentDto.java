package anton.myshareit.item.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateCommentDto (@NotBlank String text) {}

