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

import java.util.Map;

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
        Map<String, Object> parameters = Map.of("userId", userId);
        return get("/{userId}", null, parameters);
    }


    public ResponseEntity<Object> updateUser(String userDto, Long userId) {
        Map<String, Object> parameters = Map.of("userId", userId);
        return patch("/{userId}", null, parameters, userDto);
    }

    public ResponseEntity<Object> getAllUsers() {
        return get("", null, null);
    }


    public void deleteUser(Long userId) {
        Map<String, Object> parameters = Map.of("userId", userId);
        delete("/{userId}", null, parameters);

        ResponseEntity.noContent().build();

    }

}
