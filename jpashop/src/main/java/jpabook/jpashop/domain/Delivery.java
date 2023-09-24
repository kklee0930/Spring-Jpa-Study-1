package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY) // Order.delivery와 매핑
    private Order order;

    private Address address;

    @Enumerated(EnumType.STRING) // ORDINAL은 숫자로 들어가기 때문에 중간에 다른 상태가 들어가면 문제가 생길 수 있음
    private DeliveryStatus status; // READY, COMP
}
