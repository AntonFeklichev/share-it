package booking;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CreateBookingDto(@NotNull
                               Long itemId,
                               @FutureOrPresent
                               @NotNull
                               LocalDateTime start,
                               @Future
                               @NotNull
                               LocalDateTime end) {
}
