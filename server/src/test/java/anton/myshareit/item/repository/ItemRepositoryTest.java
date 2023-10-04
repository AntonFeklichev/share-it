package anton.myshareit.item.repository;

import anton.myshareit.item.entity.Item;
import anton.myshareit.user.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@DataJpaTest
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ItemRepositoryTest {

    @Autowired
    private TestEntityManager tem;
    @Autowired
    private ItemRepository itemRepository;


    @Test
    void testGetUsersItemList() {
        Long userId = tem.persistAndGetId(User.builder()
                .name("Maks")
                .email("Maks@yandex.ru")
                .build(), Long.class);

        Long itemId = tem.persistAndGetId(Item.builder()
                .name("phone")
                .description("iPhone 15")
                .available(true)
                .owner(User.builder().id(userId).build())
                .build(), Long.class);

        Page<Item> usersItemsList = itemRepository.getUsersItemsList(userId, Pageable.unpaged());

        Assertions.assertEquals(1, usersItemsList.getSize());

    }

    @Test
    void testFindItemByDescription() {

        Long userId = tem.persistAndGetId(User.builder()
                .name("Maks")
                .email("Maks@yandex.ru")
                .build(), Long.class);

        Item phone = Item.builder()
                .name("phone")
                .description("iPhone 15")
                .available(true)
                .owner(User.builder().id(userId).build())
                .build();
        Long itemId = tem.persistAndGetId(phone, Long.class);


        Page<Item> iphone = itemRepository.findItemByDescription("iphone", Pageable.unpaged());
        Item iPhone15 = iphone.getContent().get(0);

        Assertions.assertEquals(phone.getDescription(), iPhone15.getDescription());
    }


   /* @Test
    void testFindItemByItemRequestId() {
        Long userId = tem.persistAndGetId(User.builder()
                .name("Maks")
                .email("Maks@yandex.ru")
                .build(), Long.class);

        Item item = Item.builder()
                .name("phone")
                .description("iPhone 15")
                .available(true)
                .owner(User.builder().id(userId).build())
                .build();

        Long itemId = tem.persistAndGetId(item, Long.class);

        // List<Item> items = itemRepository.findItemByItemRequestId(itemId);

        Assertions.assertEquals(1, items.size());

    } */
}
