package anton.myshareit.item.controller;

import anton.myshareit.item.dto.CreateItemDto;
import anton.myshareit.item.dto.ItemDto;
import anton.myshareit.item.dto.UpdateItemDto;
import anton.myshareit.item.dto.comment.CommentDto;
import anton.myshareit.item.dto.comment.CreateCommentDto;
import anton.myshareit.item.sevice.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static anton.myshareit.constants.Constants.DEFAULT_PAGE_SIZE;
import static anton.myshareit.constants.Constants.X_SHARER_USER_ID;

@RestController
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto addItem(@RequestHeader(name = X_SHARER_USER_ID)
                           Long userId,
                           @Valid
                           @RequestBody
                           CreateItemDto createItemDto) {
        return itemService.addItem(userId, createItemDto);
    }

    @PatchMapping("/{itemId}")
    public UpdateItemDto updateItem(@PathVariable
                                    Long itemId,
                                    @RequestBody
                                    String updateItemDto,
                                    @RequestHeader(name = X_SHARER_USER_ID)
                                    Long userId) {
        return itemService.updateItem(itemId, updateItemDto, userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItem(@PathVariable
                           Long itemId,
                           @RequestHeader(name = X_SHARER_USER_ID)
                           Long userId) {
        return itemService.getItem(itemId, userId);
    }

    @GetMapping
    public List<ItemDto> getUsersItemsList(@RequestParam(name = "pageNumber", defaultValue = "0")
                                           Integer pageNumber,
                                           @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE)
                                           Integer pageSize,
                                           @RequestHeader(name = X_SHARER_USER_ID)
                                           Long userId) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        return itemService.getUsersItemsList(userId, pageRequest).getContent();
    }

    @GetMapping("/search")
    public List<ItemDto> findItem(@RequestParam(name = "text")
                                  String text,
                                  @RequestParam(name = "pageNumber", defaultValue = "0")
                                  Integer pageNumber,
                                  @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE)
                                  Integer pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        return itemService.findItemByDescription(text, pageRequest).getContent();
    }


    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@Valid
                                 @RequestBody
                                 CreateCommentDto comment,
                                 @PathVariable
                                 Long itemId,
                                 @RequestHeader(name = X_SHARER_USER_ID)
                                 Long userId) {
        return itemService.addComment(comment, itemId, userId);
    }

}
