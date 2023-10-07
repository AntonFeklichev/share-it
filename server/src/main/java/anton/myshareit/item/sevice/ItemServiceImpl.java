package anton.myshareit.item.sevice;


import anton.myshareit.booking.repository.BookingRepository;
import anton.myshareit.booking.service.BookingService;
import anton.myshareit.exceptions.AuthenticationFailedException;
import anton.myshareit.exceptions.BadRequestExceptions;
import anton.myshareit.exceptions.ItemNotFoundException;
import anton.myshareit.exceptions.UserNotFoundException;
import booking.BookingDto;
import item.CreateItemDto;
import item.CreateItemResponseDto;
import item.ItemDto;
import item.UpdateItemDto;
import comment.CommentDto;
import comment.CreateCommentDto;
import anton.myshareit.item.dtoMappers.ItemMapper;
import anton.myshareit.item.dtoMappers.UpdateItemDtoMapper;
import anton.myshareit.item.dtoMappers.commentDtoMapper.CommentDtoMapper;
import anton.myshareit.item.entity.Comment;
import anton.myshareit.item.entity.Item;
import anton.myshareit.item.repository.ItemRepository;
import anton.myshareit.item.repository.comment.CommentRepository;
import anton.myshareit.request.entity.ItemRequest;
import anton.myshareit.request.repository.RequestRepository;
import anton.myshareit.user.entity.User;
import anton.myshareit.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    private final BookingService bookingService;
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;
    private final RequestRepository requestRepository;

    @Override
    public CreateItemResponseDto addItem(Long userId, CreateItemDto createItemDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User on found"));
        Item item = ItemMapper.toNewItem(createItemDto);
        if (createItemDto.requestId() != null) {

            ItemRequest itemRequest = requestRepository.findById(createItemDto.requestId())
                    .orElseThrow(() -> new BadRequestExceptions("Request not found"));
            item.setRequest(itemRequest);
        }

        item.setOwner(user);

        Item saved = itemRepository.save(item);
        return ItemMapper.toDtoForResponse(saved);
    }

    @Override
    public UpdateItemDto updateItem(Long itemId, String updateItemDto, Long userId) {
        Item oldItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));
        authenticateOwner(oldItem, userId);
        try {
            Item updateItem = new ObjectMapper().readerForUpdating(oldItem).readValue(updateItemDto);
            itemRepository.save(updateItem);

            return UpdateItemDtoMapper.toUpdateItemDto(updateItem);

        } catch (JsonProcessingException ex) {
            ex.getMessage();
            return null;
        }
    }

    public void authenticateOwner(Item oldItem, Long userId) {
        Long ownerId = oldItem.getOwner().getId();
        if (!ownerId.equals(userId)) {
            throw new AuthenticationFailedException("You cannot update items of other users");
        }
    }

    @Override
    public ItemDto getItem(Long itemId, Long userId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));
        if (userId.equals(item.getOwner().getId())) {
            BookingDto lastBookingByItem = bookingService.getLastBookingByItem(itemId);
            BookingDto nextBookingByItem = bookingService.getNextBookingByItem(itemId);
            return ItemMapper
                    .toItemDtoWhitBookings(item, lastBookingByItem, nextBookingByItem);
        }
        return ItemMapper.toItemDto(item);
    }

    @Override
    public Page<ItemDto> getUsersItemsList(Long userId, Pageable pageRequest) {


        return itemRepository.getUsersItemsList(userId, pageRequest)
                .map(item -> ItemMapper.toItemDtoWhitBookings(item,
                        bookingService.getLastBookingByItem(item.getId()),
                        bookingService.getNextBookingByItem(item.getId())));
    }

    @Override
    public Page<ItemDto> findItemByDescription(String text, Pageable pageRequest) {
        if (text.isBlank()) {
            return Page.empty(pageRequest);
        }
        return itemRepository.findItemByDescription(text, pageRequest)
                .map(ItemMapper::toItemDto);
    }

    @Override
    public CommentDto addComment(CreateCommentDto commentDto, Long itemId, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));

        if (!bookingRepository.isItemBookedByUser(userId, itemId, LocalDateTime.now())) {
            throw new BadRequestExceptions("You are not booker of this item");
        }

        Comment comment = CommentDtoMapper.toComment(commentDto);
        comment.setItem(item);
        comment.setAuthor(user);
        comment.setCreated(LocalDateTime.now());

        commentRepository.save(comment);

        return CommentDtoMapper.toDto(comment);
    }

}
