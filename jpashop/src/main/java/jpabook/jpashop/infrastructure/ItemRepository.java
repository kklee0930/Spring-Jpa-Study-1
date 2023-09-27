package jpabook.jpashop.infrastructure;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if(item.getId() == null) {
            // item은 jpa에 저장하기 전까지는 id값이 없다.
            // id가 없다는 것은 새로 생성한 객체라는 것이다.
            em.persist(item);
        } else {
            // id가 있다는 것은 이미 db에 등록된 객체라는 것이다.
            em.merge(item); // 업데이트
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
