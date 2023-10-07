package anton.myshareit.user.controller;


import anton.myshareit.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import user.UserDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest

public class ControllerTest {

    User user;
    UserDto userDto;

    MockMvc mockMvc;

    WebApplicationContext wac;

    public ControllerTest(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.wac = wac;
    }

    @BeforeEach
    public void doSetup() {

        user = User.builder()
                .id(1L)
                .name("Maks")
                .email("Maks@yandex.ru")
                .build();
        userDto = UserDto.builder()
                .id(1l)
                .name("Maks")
                .email("Maks@yandex.ru")
                .build();

    }


    @Test
    @SneakyThrows
    @Order(2)
    public void testFindUserById() {
        mockMvc.perform(get("/users/{userId}", 1))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @Order(1)
    public void testAddUser() {

        String userDtoJson = new ObjectMapper().writeValueAsString(userDto);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDto.getId()))
                .andExpect(jsonPath("$.name").value("Maks"));
    }

    @Test
    @SneakyThrows
    @Order(3)
    public void testUpdateUser() {

        String userDtoJson = new ObjectMapper().writeValueAsString(userDto);

        mockMvc.perform(patch("/users/{userId}", userDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson))
                .andExpectAll(status().isOk());
    }

    @Test
    @SneakyThrows
    @Order(4)
    public void testGetAllUsers() {
        mockMvc.perform(get("/users"))
                .andExpectAll(status().isOk());
    }


    @Test
    @SneakyThrows
    @Order(5)
    public void testDeleteUser() {
        mockMvc.perform(delete("/users/{userId}", userDto.getId()))
                .andExpectAll(status().isNoContent());
    }


}
