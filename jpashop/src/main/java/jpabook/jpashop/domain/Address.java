package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable // 어딘가에 내장될 수 있다. 기본 생성자를 public 또는 protected로 설정해야 한다.
@Getter
public class Address {

    // 생성 시에만 값을 넣고, 변경이 불가능하게 설계하는 것이 좋다. 주소는 바뀔 일이 없기 때문에
    private String city;
    private String street;
    private String zipcode;
    protected Address() { // JPA 스펙상 만들어 놓아야 한다.
    }

    public Address(String city, String street, String zipcode) {
        this.city    = city;
        this.street  = street;
        this.zipcode = zipcode;
    }
}
