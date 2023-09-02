package anton.myshareit.request.requestMappers;

import anton.myshareit.item.dtoMappers.ItemMapper;
import anton.myshareit.request.dto.GetRequestDto;
import anton.myshareit.request.dto.RequestDto;
import anton.myshareit.request.entity.ItemRequest;
import anton.myshareit.user.dtoMappers.UserMapper;

import java.util.stream.Collectors;

public class CreateRequestMapper {

    public static ItemRequest toRequest(RequestDto requestDto) {
        return ItemRequest.builder()
                .description(requestDto.getDescription())
                .build();
    }

    public static RequestDto toDto(ItemRequest itemRequest) {
        return RequestDto.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .requester(UserMapper.toUserDto(itemRequest.getRequester()))
                .created(itemRequest.getCreated())
                .build();
    }

    public static GetRequestDto toGetRequestDto(ItemRequest itemRequest) {

        return GetRequestDto.builder()
                .id(itemRequest.getId())
                .items(itemRequest.getItems().stream()
                        .map(ItemMapper::toDtoForRequest)
                        .collect(Collectors.toList()))
                .description(itemRequest.getDescription())
                .created(itemRequest.getCreated())
                .build();
    }


}

