package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)// 다대일 관계(XToOne)에서는 기본이 즉시 로딩이다. 따라서 지연 로딩으로 설정해야 한다.
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)// 다대일 관계(XToOne)에서는 기본이 즉시 로딩이다. 따라서 지연 로딩으로 설정해야 한다.
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    // 생성자 통일을 위해 protected로 생성자 정의(해당 방법을 쓰면 OrderItem = new OrderItem() 형식의 생성자에 제한을 건다.)
    // @NoArgsConstructor(access = AccessLevel.PROTECTED)로 lombok으로 사용가능.
//    protected OrderItem() {
//    }

    //== 생성 메서드 ==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count); // 주문량만큼 재고 마이너스 처리
        return orderItem;
    }

    //== 비즈니스 로직 ==//
    public void cancel() {
        getItem().addStock(count); // 재고 수량 원복해주기
    }

    //== 조회 로직 ==//
    /**
     * 주문상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
