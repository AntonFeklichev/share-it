package anton.myshareit.exceptions;

public class ItemAlreadyBookingException extends RuntimeException{
    public ItemAlreadyBookingException(String message) {
        super(message);
    }
}
