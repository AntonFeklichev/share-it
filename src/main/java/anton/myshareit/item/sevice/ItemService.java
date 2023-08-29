package anton.myshareit.item.sevice;

import anton.myshareit.item.dto.CreateItemDto;
import anton.myshareit.item.dto.ItemDto;
import anton.myshareit.item.dto.ListOfItemsDto;
import anton.myshareit.item.dto.UpdateItemDto;
import anton.myshareit.item.dto.comment.CommentDto;
import anton.myshareit.item.dto.comment.CreateCommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {

    ItemDto addItem(Long userId, CreateItemDto createItemDto);
    UpdateItemDto updateItem(Long itemId, String updateItemDto, Long userId);
    ItemDto getItem(Long itemId, Long userId);
    Page<ItemDto> getUsersItemsList(Long userId, Pageable pageRequest);
    Page<ItemDto> findItemByDescription(String text, Pageable pageRequest);
    CommentDto addComment(CreateCommentDto comment, Long itemId, Long userId);
}
