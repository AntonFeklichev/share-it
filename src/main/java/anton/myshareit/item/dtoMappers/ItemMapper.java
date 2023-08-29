package anton.myshareit.item.dtoMappers;

import anton.myshareit.booking.dto.BookingDto;
import anton.myshareit.item.dto.CreateItemDto;
import anton.myshareit.item.dto.ItemDto;
import anton.myshareit.item.dtoMappers.commentDtoMapper.CommentDtoMapper;
import anton.myshareit.item.entity.Item;
import anton.myshareit.user.dtoMappers.UserMapper;

import java.util.stream.Collectors;

public class ItemMapper {

    public static ItemDto toItemDto(Item item) {
        return ItemDto.builder().id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(UserMapper.toUserDto(item.getOwner()))
                .request(item.getRequest())
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
                .request(item.getRequest())
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
                .request(itemDto.getRequest())
                .build();
    }

    public static Item toNewItem(CreateItemDto createItemDto) {

        return Item.builder()
                .name(createItemDto.name())
                .description(createItemDto.description())
                .available(createItemDto.isAvailable())
                .build();
    }

}
