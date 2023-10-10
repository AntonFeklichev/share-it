package anton.myshareit.gateway.item.item.controller;

import comment.CreateCommentDto;
import item.CreateItemDto;
import anton.myshareit.gateway.item.item.client.ItemClient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static constants.Constants.DEFAULT_PAGE_SIZE;
import static constants.Constants.X_SHARER_USER_ID;

@RestController
@RequestMapping(path = "/items")
@Slf4j
@Validated
public class ItemController {

    private final ItemClient itemClient;

    @Autowired
    public ItemController(ItemClient itemClient) {
        this.itemClient = itemClient;
    }

    @PostMapping
    public ResponseEntity<Object> addItem(@RequestHeader(name = X_SHARER_USER_ID)
                                          Long userId,
                                          @Valid
                                          @RequestBody
                                          CreateItemDto createItemDto) {
        log.info("Add Item by User id {}", userId);
        return itemClient.addItem(userId, createItemDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@PathVariable
                                             Long itemId,
                                             @RequestBody
                                             String updateItemDto,
                                             @RequestHeader(name = X_SHARER_USER_ID)
                                             Long userId) {
        log.info("Updating Item by id{} by User id {}", itemId, userId);
        return itemClient.updateItem(itemId, updateItemDto, userId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItem(@PathVariable
                                          Long itemId,
                                          @RequestHeader(name = X_SHARER_USER_ID)
                                          Long userId) {
        log.info("Getting Item by id {} by User id {}", itemId, userId);
        return itemClient.getItem(itemId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getUsersItemsList(@RequestParam(name = "from", defaultValue = "0")
                                                    @PositiveOrZero
                                                    Integer from,
                                                    @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE)
                                                    @PositiveOrZero
                                                    Integer size,
                                                    @RequestHeader(name = X_SHARER_USER_ID)
                                                    Long userId) {
        log.info("Getting Item list by User id {}, Paging parameters: from {} size {}", userId, from, size);
        return itemClient.getUsersItemsList(from, size, userId);

    }


    @GetMapping("/search")
    public ResponseEntity<Object> findItem(@RequestParam(name = "text")
                                           String text,
                                           @RequestParam(name = "from", defaultValue = "0")
                                           @PositiveOrZero
                                           Integer from,
                                           @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE)
                                           @Positive
                                           Integer size,
                                           @RequestHeader(name = X_SHARER_USER_ID)
                                           Long userId) {
        log.info("Searching by text {}. Paging parameters: from {} size {}", text, from, size);
        return itemClient.findItem(text, from, size, userId);
    }


    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@Valid
                                             @RequestBody
                                             CreateCommentDto comment,
                                             @PathVariable
                                             Long itemId,
                                             @RequestHeader(name = X_SHARER_USER_ID)
                                             Long userId) {
        return itemClient.addComment(comment, itemId, userId);
    }


}
