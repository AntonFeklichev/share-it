package item.client;

import client.BaseClient;
import comment.CreateCommentDto;

import item.CreateItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Map;

@Component
public class ItemClient extends BaseClient {

    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${my-share-it.sever.URL}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> addItem(Long userId, CreateItemDto createItemDto) {
        return post("", userId, null, createItemDto);
    }

    public ResponseEntity<Object> updateItem(Long itemId, String updateItemDto, Long userId) {

        Map<String, Object> parameters = Map.of("/{itemId}", itemId);

        return patch("/{itemId}", userId, parameters, updateItemDto);
    }

    public ResponseEntity<Object> getItem(Long itemId, Long userId) {
        Map<String, Object> parameters = Map.of("/{itemId}", itemId);

        return get("/{itemId}", userId, parameters);
    }

    public ResponseEntity<Object> getUsersItemsList(Integer from, Integer size, Long userId) {

        Map<String, Object> parameters = Map.of("from", from,
                "size", size);

        return get("?from={from}&size={size}", userId, parameters);
    }

    public ResponseEntity<Object> findItem(String text, Integer from, Integer size, Long userId) {

        Map<String, Object> parameters = Map.of("text", text,
                "from", from,
                "size", size);

        return get("/search?text={text}&from={from}&size={size}", userId, parameters);
    }

    public ResponseEntity<Object> addComment(CreateCommentDto comment, Long itemId, Long userId) {
        Map<String, Object> parameters = Map.of("itemId", itemId);

        return post("/{itemId}/comment", userId, parameters, comment);
    }


}
