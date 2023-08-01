package anton.myshareit.item;

import anton.myshareit.item.dto.ItemDto;
import anton.myshareit.item.dto.ListOfItemsDto;
import anton.myshareit.item.dto.UpdateItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto addItem(@RequestHeader(name = X_Sharer_User_Id)
                           Long userId,
                           ItemDto itemDto) {
        return itemService.addItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public UpdateItemDto updateItem(@PathVariable
                                    Long itemId,
                                    @RequestBody
                                    String updateItemDto,
                                    @RequestHeader(name = X_Sharer_User_Id)
                                    Long userId) {
        return itemService.updateItem(itemId, updateItemDto, userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItem(@PathVariable
                           Long itemId) {
        return itemService.getItem(itemId);
    }

    @GetMapping
    public List<ListOfItemsDto> getItemList(@RequestHeader(name = X_Sharer_User_Id)
                                            Long userId) {
        return itemService.getItemList(userId);
    }

    @GetMapping("/search?text={text}")
    public ItemDto findItem(@RequestParam
                            String text) {
        return itemService.findItem(text);
    }
}
