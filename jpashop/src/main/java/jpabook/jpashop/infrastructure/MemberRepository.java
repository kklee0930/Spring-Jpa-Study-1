package jpabook.jpashop.infrastructure;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor // final이 있는 필드만 가지고 생성자를 만들어준다.
public class MemberRepository {

    private final EntityManager em;

//    @PersistenceContext // 스프링이 엔티티 매니저를 주입해준다.
//    private EntityManager em;

    // 회원 저장
    public void save(Member member) {
        em.persist(member);
    }

    // 회원 조회
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    // 회원 전체 조회
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class) // 첫번째: JPQL, 두번째: 반환 타입
                .getResultList();
    }

    // 회원 이름으로 조회
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name")
                .setParameter("name", name)
                .getResultList();
    }
}
