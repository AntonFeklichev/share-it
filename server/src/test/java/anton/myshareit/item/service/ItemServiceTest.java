package anton.myshareit.item.service;

import anton.myshareit.booking.dto.BookingDto;
import anton.myshareit.booking.entity.BookingStatus;
import anton.myshareit.booking.repository.BookingRepository;
import anton.myshareit.booking.service.BookingService;
import item.CreateItemDto;
import item.CreateItemResponseDto;
import anton.myshareit.item.dto.ItemDto;
import item.UpdateItemDto;
import item.comment.CommentDto;
import item.comment.CreateCommentDto;
import anton.myshareit.item.entity.Item;
import anton.myshareit.item.repository.ItemRepository;
import anton.myshareit.item.repository.comment.CommentRepository;
import anton.myshareit.item.sevice.ItemServiceImpl;
import anton.myshareit.request.repository.RequestRepository;
import anton.myshareit.user.dto.UserDto;
import anton.myshareit.user.entity.User;
import anton.myshareit.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
    @InjectMocks
    private ItemServiceImpl itemServiceImpl;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookingService bookingService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private RequestRepository requestRepository;

    User owner = User.builder()
            .id(1L)
            .name("Maks")
            .email("maks@yandex.ru")
            .build();
    User booker = User.builder()
            .id(2L)
            .name("Artem")
            .email("artem@yandex.ru")
            .build();
    UserDto bookerDto = UserDto.builder()
            .id(2L)
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
            .description("iPhone 15 proMax")
            .available(true)
            .build();
    Item item = Item.builder()
            .id(1L)
            .name("phone")
            .description("iPhone 15")
            .available(true)
            .owner(owner)
            .build();

    ItemDto itemDto = ItemDto.builder()
            .id(item.getId())
            .name(item.getName())
            .description(item.getDescription())
            .available(item.isAvailable())
            .build();

    BookingDto nextBookingByItem = BookingDto.builder()
            .start(LocalDateTime.now().plusDays(1))
            .end(LocalDateTime.now().plusDays(2))
            .item(itemDto)
            .booker(bookerDto)
            .status(BookingStatus.APPROVED)
            .build();
    BookingDto lastBookingByItem = BookingDto.builder()
            .start(LocalDateTime.now().minusDays(2))
            .end(LocalDateTime.now().minusDays(1))
            .item(itemDto)
            .booker(bookerDto)
            .status(BookingStatus.APPROVED)
            .build();

    CommentDto commentDto = CommentDto.builder()
            .id(1L)
            .text("i want this phone")
            .item(itemDto)
            .authorName("Artem")
            .build();

    CreateCommentDto createCommentDto = CreateCommentDto.builder()
            .text("Soviet diskPhone is better")
            .build();

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void testAddItem() {

        Mockito.when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(owner));

        Mockito.when(itemRepository.save(any(Item.class))).thenReturn(item);


        CreateItemResponseDto createItemResponseDto = itemServiceImpl
                .addItem(owner.getId(), createItemDto);

        Assertions.assertEquals("iPhone 15", createItemResponseDto.description());

    }

    @Test
    @SneakyThrows
    public void testUpdateItem() {
        Mockito.when(itemRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(item));


        String updateItemDtoString = objectMapper.writeValueAsString(updateItemDto);

        UpdateItemDto resultUpdateItemDto = itemServiceImpl
                .updateItem(item.getId(), updateItemDtoString, owner.getId());
        Assertions.assertEquals("iPhone 15 proMax", resultUpdateItemDto.getDescription());

    }

    @Test
    public void testGetItem() {
        Mockito.when(itemRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(item));
        Mockito.when(bookingService.getLastBookingByItem(anyLong()))
                .thenReturn(lastBookingByItem);
        Mockito.when(bookingService.getNextBookingByItem(anyLong()))
                .thenReturn(nextBookingByItem);

        ItemDto resultItemDto = itemServiceImpl.getItem(item.getId(), owner.getId());

        Assertions.assertEquals(item.getDescription(), resultItemDto.getDescription());
    }

    @Test
    public void testGetUsersItemsList() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        Page<Item> pageItemList = new PageImpl<>(itemList);

        Mockito.when(itemRepository.getUsersItemsList(anyLong(), any()))
                .thenReturn(pageItemList);

        Page<ItemDto> pageItemDto = itemServiceImpl
                .getUsersItemsList(owner.getId(), Pageable.unpaged());

        ItemDto resultItemDto = pageItemDto.getContent().get(0);

        Assertions.assertEquals(itemDto.getDescription(), resultItemDto.getDescription());
    }

    @Test
    public void testAddComment() {

        Mockito.when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(booker));
        Mockito.when(itemRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(item));
        Mockito.when(bookingRepository.isItemBookedByUser(anyLong(),anyLong(),any()))
                .thenReturn(true);

        CommentDto resultCommentDto = itemServiceImpl
                .addComment(createCommentDto, item.getId(), booker.getId());

        Assertions.assertEquals("Soviet diskPhone is better", resultCommentDto.getText());
    }

}
