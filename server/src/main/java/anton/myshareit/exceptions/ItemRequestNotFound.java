package anton.myshareit.exceptions;

import anton.myshareit.request.entity.ItemRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.NOT_FOUND)
public class ItemRequestNotFound extends RuntimeException{
    public ItemRequestNotFound(String message) {
        super(message);
    }
}
