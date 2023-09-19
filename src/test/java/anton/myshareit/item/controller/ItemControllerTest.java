package anton.myshareit.item.controller;

import anton.myshareit.booking.entity.Booking;
import anton.myshareit.booking.entity.BookingStatus;
import anton.myshareit.item.dto.CreateItemDto;
import anton.myshareit.item.dto.ItemDto;
import anton.myshareit.item.dto.UpdateItemDto;
import anton.myshareit.item.entity.Item;
import anton.myshareit.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static anton.myshareit.constants.Constants.X_SHARER_USER_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureTestEntityManager

public class ItemControllerTest {
    User owner = User.builder()
            .name("Maks")
            .email("maks@yandex.ru")
            .build();
    User booker = User.builder()
            .name("Artem")
            .email("artem@yandex.ru")
            .build();
    CreateItemDto createItemDto = CreateItemDto.builder()
            .name("phone")
            .description("iPhone 15")
            .available(true)
            .requestId(null)
            .build();

    UpdateItemDto updateItemDto = UpdateItemDto.builder()
            .id(1L)
            .name("phone")
            .description("iPone 15 proMax")
            .available(true)
            .build();
    Item item = Item.builder()
            .name("phone")
            .description("iPhone 15")
            .available(true)
            .owner(owner)
            .build();

    Booking nextBookingByItem = Booking.builder()
            .start(LocalDateTime.now().plusDays(1))
            .end(LocalDateTime.now().minusDays(1))
            .item(item)
            .booker(booker)
            .status(BookingStatus.WAITING)
            .build();
    Booking lastBookingByItem = Booking.builder()
            .start(LocalDateTime.now().minusDays(2))
            .end(LocalDateTime.now().minusDays(1))
            .item(item)
            .booker(booker)
            .status(BookingStatus.WAITING)
            .build();

    ObjectMapper objectMapper = new ObjectMapper();
    MockMvc mockMvc;
    WebApplicationContext wac;
    @Autowired
    TestEntityManager testEntityManager;

    public ItemControllerTest(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.wac = wac;
    }

    @Test
    @SneakyThrows
    @Transactional //TODO понять почему сработало
    public void testAddItem() {
        String createItemDtoJson = new ObjectMapper().writeValueAsString(createItemDto);

        testEntityManager.persistAndFlush(owner);

        mockMvc.perform(post("/items")
                        .content(createItemDtoJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(X_SHARER_USER_ID, owner.getId()))
                .andExpect(status().isOk())
                .andExpectAll(jsonPath("$.name").value("phone"),
                        jsonPath("$.description").value("iPhone 15"));

        Assertions.assertTrue(testEntityManager.find(Item.class, 1L)
                .isAvailable());//TODO проверить сохранение айтема
    }


    @Test
    @SneakyThrows
    @Transactional
    public void testUpdateItem() {
        String updateItemDtoJson = new ObjectMapper().writeValueAsString(updateItemDto);
        testEntityManager.persistAndFlush(owner);
        testEntityManager.persistAndFlush(item);

        mockMvc.perform(patch("/items/{itemId}", item.getId())
                .content(updateItemDtoJson)
                .contentType(MediaType.APPLICATION_JSON)
                .header(X_SHARER_USER_ID, owner.getId())
        ).andExpect(status().isOk());
    }


    @Test
    @SneakyThrows
    @Transactional
    public void testGetItem() {
        testEntityManager.persistAndFlush(owner);
        testEntityManager.persistAndFlush(booker);
        testEntityManager.persistAndFlush(item);
        testEntityManager.persistAndFlush(lastBookingByItem);
        testEntityManager.persistAndFlush(nextBookingByItem);

        MvcResult mvcResult = mockMvc.perform(get("/items/{itemId}", item.getId())
                        .header(X_SHARER_USER_ID, owner.getId()))
                .andExpect(status().isOk())
                .andReturn();
        String resultString = mvcResult.getResponse().getContentAsString();
        ItemDto itemDto = objectMapper.readValue(resultString, ItemDto.class);

        Assertions.assertEquals(nextBookingByItem.getId(), itemDto.getNextBooking().getId());
        Assertions.assertEquals(lastBookingByItem.getId(), itemDto.getLastBooking().getId());
    }

}
