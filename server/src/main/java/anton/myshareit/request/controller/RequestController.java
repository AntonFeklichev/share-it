package anton.myshareit.request.controller;

import anton.myshareit.constants.Constants;
import anton.myshareit.request.dto.GetRequestDto;
import anton.myshareit.request.dto.RequestDto;
import anton.myshareit.request.service.RequestService;
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
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public RequestDto createRequest(@RequestHeader(name = X_SHARER_USER_ID)
                                    Long userId,
                                    @Valid
                                    @RequestBody
                                    RequestDto requestDto) {
        return requestService.createRequest(userId, requestDto);
    }

    @GetMapping
    public List<GetRequestDto> getRequestorRequestList(@RequestHeader(name = X_SHARER_USER_ID)
                                                       Long userId) {
        return requestService.getRequestorRequestList(userId);
    }

    @GetMapping("/all")
    public List<GetRequestDto> getRequestList(@RequestParam(name = "from", defaultValue = "0")
                                              Integer from,
                                              @RequestParam(name = "size",
                                                      defaultValue = DEFAULT_PAGE_SIZE)
                                              @PositiveOrZero
                                              Integer size,
                                              @RequestHeader(name = Constants.X_SHARER_USER_ID)
                                              @Positive
                                              Long userId) {
        Pageable pageRequest = PageRequest.of(from, size);
        return requestService.getRequestList(userId, pageRequest).getContent();
    }


    @GetMapping("/{requestId}")
    public GetRequestDto getRequest(@PathVariable
                                    Long requestId,
                                    @RequestHeader(name = X_SHARER_USER_ID)
                                    Long userId) {
        return requestService.getRequest(requestId, userId);
    }
}
