package anton.myshareit.gateway.user.user.client;

import anton.myshareit.gateway.client.client.BaseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import user.UserDto;

@Component
public class UserClient extends BaseClient {

    private static final String API_PREFIX = "/users";

    @Autowired
    public UserClient(@Value("${my-share-it.server.URL}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> addUser(UserDto userDto) {
        return post("", null, null, userDto);
    }


    public ResponseEntity<Object> findUserById(Long userId) {
        return get("/{userId}", userId, null);
    }


    public ResponseEntity<Object> updateUser(String userDto, Long userId) {
        return patch("/{userId}", userId, null, userDto);
    }

    public ResponseEntity<Object> getAllUsers() {
        return get("", null, null);
    }


    public void deleteUser(Long userId) {
        delete("/{userId}", userId, null);

        ResponseEntity.noContent().build();

    }

}
