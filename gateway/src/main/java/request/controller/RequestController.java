package request.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import request.RequestDto;
import request.client.RequestClient;

import static constants.Constants.DEFAULT_PAGE_SIZE;
import static constants.Constants.X_SHARER_USER_ID;

@RestController
@RequestMapping(name = "/requests")
@Slf4j
@Validated
@RequiredArgsConstructor
public class RequestController {

    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> createRequest(@RequestHeader(name = X_SHARER_USER_ID)
                                                Long userId,
                                                @Valid
                                                @RequestBody
                                                RequestDto requestDto) {

        log.info("Creating request by User id {}", userId);
        return requestClient.createRequest(userId, requestDto);
    }

    @GetMapping
    public ResponseEntity<Object> getRequestorRequestList(@RequestHeader(name = X_SHARER_USER_ID)
                                                          Long userId) {
        log.info("Getting request list by User id {}", userId);
        return requestClient.getRequestorRequestList(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getRequestList(@RequestParam(name = "from", defaultValue = "0")
                                                 Integer from,
                                                 @RequestParam(name = "size",
                                                         defaultValue = DEFAULT_PAGE_SIZE)
                                                 @PositiveOrZero
                                                 Integer size,
                                                 @RequestHeader(name = X_SHARER_USER_ID)
                                                 @Positive
                                                 Long userId) {
        log.info("Get all request by User id {} whit page parameters", userId);
        return requestClient.getRequestList(from, size, userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequest(@PathVariable
                                             Long requestId,
                                             @RequestHeader(name = X_SHARER_USER_ID)
                                             Long userId) {
        log.info("Get request by id {} by User id {}", requestId, userId);
        return requestClient.getRequest(requestId, userId);
    }


}
