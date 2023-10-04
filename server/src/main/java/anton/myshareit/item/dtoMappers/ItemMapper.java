package anton.myshareit.item.dtoMappers;

import anton.myshareit.booking.dto.BookingDto;
import item.CreateItemDto;
import item.CreateItemResponseDto;
import item.ItemDto;
import item.ItemForRequestDto;
import anton.myshareit.item.dtoMappers.commentDtoMapper.CommentDtoMapper;
import anton.myshareit.item.entity.Item;
import anton.myshareit.request.requestMappers.CreateRequestMapper;
import anton.myshareit.user.dtoMappers.UserMapper;

import java.util.stream.Collectors;

public class ItemMapper {

    public static ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(UserMapper.toUserDto(item.getOwner()))
                .request(CreateRequestMapper.toGetRequestDto(item.getRequest()))
                .comments(item.getComments()
                        .stream()
                        .map(CommentDtoMapper::toDtoWithoutItem)
                        .collect(Collectors.toList()))
                .build();

    }

    public static ItemDto toItemDtoInAddItemMethod(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(UserMapper.toUserDto(item.getOwner()))
                .comments(item.getComments()
                        .stream()
                        .map(CommentDtoMapper::toDtoWithoutItem)
                        .collect(Collectors.toList()))
                .build();

    }


    public static ItemDto toItemDtoWhitBookings(Item item, BookingDto lastBooking, BookingDto nextBooking) {
        return ItemDto.builder().id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(UserMapper.toUserDto(item.getOwner()))
                .request(CreateRequestMapper.toGetRequestDto(item.getRequest()))
                .lastBooking(lastBooking)
                .nextBooking(nextBooking)
                .comments(item.getComments()
                        .stream()
                        .map(CommentDtoMapper::toDto)
                        .collect(Collectors.toList()))
                .build();

    }


    public static Item toItem(ItemDto itemDto) {

        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .owner(itemDto.getOwner() == null ? null : UserMapper.toUser(itemDto.getOwner()))
                .build();
    }

    public static Item toNewItem(CreateItemDto createItemDto) {

        return Item.builder()
                .name(createItemDto.name())
                .description(createItemDto.description())
                .available(createItemDto.isAvailable())
                .build();
    }

    public static ItemForRequestDto toDtoForRequest(Item item) {
        return ItemForRequestDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.isAvailable())
                .requestId(item.getRequest().getId())
                .build();
    }

    public static CreateItemResponseDto toDtoForResponse(Item item) {
        return CreateItemResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .requestId(item.getRequest() == null ? null : item.getRequest().getId())
                .available(item.isAvailable())
                .build();
    }


}
