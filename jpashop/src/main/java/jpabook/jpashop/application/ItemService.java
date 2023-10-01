package jpabook.jpashop.application;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.infrastructure.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional // Dirty Checking을 이용한 변경감지 기능을 통한 업데이트
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);

        // 아래처럼 일일이 setter 사용대신 메서드 하나로 처리
//        findItem.setPrice(price);
//        findItem.setName(name);
//        findItem.setStockQuantity(stockQuantity);
        findItem.change(price, name, stockQuantity);

    }

    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }
}
