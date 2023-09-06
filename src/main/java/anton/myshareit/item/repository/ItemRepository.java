package anton.myshareit.item.repository;

import anton.myshareit.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(""" 
            SELECT i
            FROM Item i
            WHERE i.owner.id = ?1
            ORDER BY i.id ASC""")
    Page<Item> getUsersItemsList(Long userId, Pageable pageRequest);

    @Query("SELECT i " +
            "FROM Item i " +
            "WHERE (LOWER(i.name) LIKE LOWER('%' || ?1 || '%') OR LOWER(i.description) LIKE LOWER('%' || ?1 ||'%')) " +
            "AND i.available = true")
    Page<Item> findItemByDescription(String text, Pageable pageRequest);

    @Query("""
            SELECT i
            FROM Item i
            WHERE i.request.id = ?1
            """)
    List<Item> findItemByItemRequestId(Long id);
}


