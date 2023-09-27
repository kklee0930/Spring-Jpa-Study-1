//package jpabook.jpashop;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jpabook.jpashop.domain.Member;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class MemberRepository {
//
//    @PersistenceContext // 엔티티 매니저를 주입받는다.
//    private EntityManager em;
//
//    public Long save(Member member) {
//        em.persist(member); // 데이터베이스에 member 객체를 저장한다. persist: 영속화
//        return member.getId();
//    }
//
//    public Member find(Long id) {
//        return em.find(Member.class, id); // em.find
//    }
//}
