package anton.myshareit.item.sevice;

import anton.myshareit.item.dto.*;
import item.CreateItemDto;
import item.CreateItemResponseDto;
import item.UpdateItemDto;
import item.comment.CommentDto;
import item.comment.CreateCommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {

    CreateItemResponseDto addItem(Long userId, CreateItemDto createItemDto);
    UpdateItemDto updateItem(Long itemId, String updateItemDto, Long userId);
    ItemDto getItem(Long itemId, Long userId);
    Page<ItemDto> getUsersItemsList(Long userId, Pageable pageRequest);
    Page<ItemDto> findItemByDescription(String text, Pageable pageRequest);
    CommentDto addComment(CreateCommentDto comment, Long itemId, Long userId);
}
