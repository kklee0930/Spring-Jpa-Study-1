package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // 테이블 이름을 orders로 지정한다.
@Getter
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계(XToOne)에서는 기본이 즉시 로딩이다. 따라서 지연 로딩으로 설정해야 한다.
    @JoinColumn(name = "member_id") // 외래 키를 매핑한다.
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // order만 persist하면 orderItem도 persist된다.
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문 시간

    @Enumerated(EnumType.STRING) // ORDINAL은 숫자로 들어가기 때문에 추후에 순서가 바뀌면 문제가 생길 수 있다.
    private OrderStatus status; // 주문 상태 [ORDER, CANCEL]

    // 연관관계 메서드(연관관계 편의 메서드. 양방향일 때 쓰면 좋다.)
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this); // 연관관계의 주인이 OrderItem이므로 OrderItem에도 설정해준다.
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this); // 연관관계의 주인이 Delivery이므로 Delivery에도 설정해준다.
    }
}
