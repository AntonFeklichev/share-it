package anton.myshareit.request.repository;

import anton.myshareit.request.entity.ItemRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<ItemRequest, Long> {

    @Query("""
            SELECT ir
            FROM ItemRequest ir
            WHERE ir.requester.id = ?1
            ORDER BY ir.created
            DESC          
            """)
    List<ItemRequest> getRequestorRequestList(Long userId);

    @Query("""
            SELECT ir
            FROM ItemRequest ir
            WHERE ir.requester.id <> ?1
            ORDER BY ir.created
            DESC
            """)
    Page<ItemRequest> getRequestList(Long userId, Pageable pageRequest);

}
