package request.client;

import client.BaseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import request.RequestDto;

import java.util.Map;

@Component
public class RequestClient extends BaseClient {
    private static final String API_PREFIX = "/requests";

    @Autowired
    public RequestClient(@Value("${my-share-it.sever.URL}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }


    public ResponseEntity<Object> createRequest(Long userId, RequestDto requestDto) {

        return post("", userId, null, requestDto);
    }


    public ResponseEntity<Object> getRequestorRequestList(Long userId) {
        return get("", userId, null);
    }

    public ResponseEntity<Object> getRequestList(Integer from, Integer size, Long userId) {
        Map<String, Object> parameters = Map.of("from", from,
                "size", size);
        return get("/all?from={from}&size={size}", userId, parameters);
    }

    public ResponseEntity<Object> getRequest(Long requestId, Long userId) {
        Map<String, Object> parameters = Map.of("requestId", requestId);
        return get("/{requestId}", userId, parameters);
    }


}
