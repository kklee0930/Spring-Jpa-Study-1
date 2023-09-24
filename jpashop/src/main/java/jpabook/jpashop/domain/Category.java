package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    @OneToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"), // 중간테이블의 category_id
            inverseJoinColumns = @JoinColumn(name = "item_id") // category_item 테이블에서 아이템으로 들어가는 매핑
    ) // 중간테이블 매핑(테이블과 테이블 매핑)
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)// 다대일 관계(XToOne)에서는 기본이 즉시 로딩이다. 따라서 지연 로딩으로 설정해야 한다.
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent") // 자기 자신을 참조하는 것
    private List<Category> child = new ArrayList<>();

    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);

    }
}
