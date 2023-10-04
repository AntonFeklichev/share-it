package anton.myshareit.item.controller;

import item.CreateItemDto;
import item.CreateItemResponseDto;
import anton.myshareit.item.dto.ItemDto;
import item.UpdateItemDto;
import item.comment.CommentDto;
import item.comment.CreateCommentDto;
import anton.myshareit.item.sevice.ItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static anton.myshareit.constants.Constants.DEFAULT_PAGE_SIZE;
import static anton.myshareit.constants.Constants.X_SHARER_USER_ID;

@RestController
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public CreateItemResponseDto addItem(@RequestHeader(name = X_SHARER_USER_ID)
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
    public List<ItemDto> getUsersItemsList(@RequestParam(name = "from", defaultValue = "0")
                                           @PositiveOrZero
                                           Integer from,
                                           @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE)
                                           @PositiveOrZero
                                           Integer size,
                                           @RequestHeader(name = X_SHARER_USER_ID)
                                           Long userId) {
        Pageable pageRequest = PageRequest.of(from, size);
        return itemService.getUsersItemsList(userId, pageRequest).getContent();
    }

    @GetMapping("/search")
    public List<ItemDto> findItem(@RequestParam(name = "text")
                                  String text,
                                  @RequestParam(name = "from", defaultValue = "0")
                                  @PositiveOrZero
                                  Integer from,
                                  @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE)
                                  @Positive
                                  Integer size) {
        Pageable pageRequest = PageRequest.of(from, size);
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
