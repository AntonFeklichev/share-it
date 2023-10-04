package anton.myshareit.request.service;

import anton.myshareit.request.dto.GetRequestDto;
import anton.myshareit.request.dto.RequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RequestService {

    RequestDto createRequest(Long userId, RequestDto requestDto);

    List<GetRequestDto> getRequestorRequestList(Long userId);

    Page<GetRequestDto> getRequestList(Long userId, Pageable pageRequest);

    GetRequestDto getRequest(Long requestId, Long userId);
}
