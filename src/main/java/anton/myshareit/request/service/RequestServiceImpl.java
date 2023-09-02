package anton.myshareit.request.service;

import anton.myshareit.exceptions.BadRequestExceptions;
import anton.myshareit.exceptions.UserNotFoundException;
import anton.myshareit.item.repository.ItemRepository;
import anton.myshareit.request.dto.GetRequestDto;
import anton.myshareit.request.dto.RequestDto;
import anton.myshareit.request.entity.ItemRequest;
import anton.myshareit.request.repository.RequestRepository;
import anton.myshareit.request.requestMappers.CreateRequestMapper;
import anton.myshareit.user.entity.User;
import anton.myshareit.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;


    @Override
    public RequestDto createRequest(Long userId, RequestDto requestDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        ItemRequest request = CreateRequestMapper.toRequest(requestDto);
        request.setRequester(user);
        request.setCreated(LocalDateTime.now());

        requestRepository.save(request);
        return CreateRequestMapper.toDto(request);
    }

    @Override
    public List<GetRequestDto> getRequestorRequestList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return requestRepository.getRequestorRequestList(userId)
                .stream()
                .map(CreateRequestMapper::toGetRequestDto)
                .collect(Collectors.toList());

/*        return requestRepository.getRequestorRequestList(userId)
                .stream()
                .map(itemRequest -> {
                    List<Item> itemByItemRequestId = itemRepository.findItemByItemRequestId(itemRequest.getId());
                    GetRequestDto getRequestDto = CreateRequestMapper.toGetRequestDto(itemRequest);
                    getRequestDto.setItems(itemByItemRequestId
                            .stream()
                            .map(ItemMapper::toDtoForRequest)
                            .collect(Collectors.toList()));
                    return getRequestDto;
                })
                .collect(Collectors.toList());*/
    }

    @Override
    public Page<GetRequestDto> getRequestList(Pageable pageRequest) {

        return requestRepository.getRequestList(pageRequest)
                .map(CreateRequestMapper::toGetRequestDto);
    }

    @Override
    public GetRequestDto getRequest(Long requestId) {
        ItemRequest itemRequest = requestRepository.findById(requestId)
                .orElseThrow(() -> new BadRequestExceptions("Request not found"));

        return CreateRequestMapper.toGetRequestDto(itemRequest);
    }

}
