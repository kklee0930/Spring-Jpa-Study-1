package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // 내장 타입을 사용했다.
    private Address address;

    // mappedBy: 연관관계의 주인이 아니다. 읽기 전용이다. (읽기만 가능하고, 변경은 불가능하다.)
    @OneToMany(mappedBy = "member") // Order 테이블에 있는 member 컬럼에 의해 매핑된다.
    private List<Order> orders = new ArrayList<>();
}
