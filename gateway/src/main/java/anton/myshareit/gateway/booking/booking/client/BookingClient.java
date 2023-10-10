package anton.myshareit.gateway.booking.booking.client;

import booking.BookingStatus;
import booking.CreateBookingDto;
import anton.myshareit.gateway.client.client.BaseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Map;

@Component
public class BookingClient extends BaseClient {

    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${my-share-it.server.URL}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> addBooking(CreateBookingDto createBookingDto, Long userId) {
        return post("", userId, null, createBookingDto);
    }


    public ResponseEntity<Object> approveBooking(Long bookingId, Boolean approved, Long userId) {

        Map<String, Object> parameters = Map.of("/{bookingId}", bookingId,
                "approved", approved);
        return patch("/{bookingId}", userId, parameters, null);
    }

    public ResponseEntity<Object> getBookingStatus(Long bookingId, Long userId) {
        Map<String, Object> parameters = Map.of("/{bookingId}", bookingId);
        return get("/{bookingId}", userId, parameters);
    }

    public ResponseEntity<Object> getBookingList(BookingStatus state, Long userId, Integer from, Integer size) {

        Map<String, Object> parameters = Map.of("state", state.name(),
                "from", from,
                "size", size);
        return get("?state={state}&from={from}&size={size}", userId, parameters);

    }

    public ResponseEntity<Object> getBookingListByOwner(BookingStatus state, Long userId, Integer from, Integer size) {

        Map<String, Object> parameters = Map.of("state", state.name(),
                "from", from,
                "size", size);

        return get("?state={state}&from={from}&size={size}", userId, parameters);
    }


}
