package anton.myshareit.exceptions.handler;

import anton.myshareit.booking.controller.BookingController;
import anton.myshareit.item.controller.ItemController;
import anton.myshareit.user.controller.UserController;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"anton.myshareit"},
        basePackageClasses = {BookingController.class, ItemController.class, UserController.class})
public class ControllerExceptionHandler {


    private record ErrorResponse(String error) {
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse getErrorResponse(ValidationException e) {
        return new ErrorResponse(e.getMessage());
    }
}
