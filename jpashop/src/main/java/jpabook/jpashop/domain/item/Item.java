package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
// 상속관계 매핑 전략을 지정한다. SINGLE_TABLE은 한 테이블에 다 때려박는 전략이다.
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items") // // 실무에서는 절대 사용하지 말자. 다대다 관계를 일대다, 다대일 관계로 풀어내는 것이 좋다.
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//
    // 데이터를 가지고 있는 item에 stockquantity update를 가지고 있는 것이 더욱 객체지향적이다.

    /**
     * stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            // 재고가 부족하다는 예외를 던진다. 예외를 따로 생성해준다.
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

    /**
     * stock 정보 수정
     */
    public void change(int price, String name, int stockQuantity) {
        this.price = price;
        this.name = name;
        this.stockQuantity = stockQuantity;
    }
}
