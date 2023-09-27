package jpabook.jpashop.application;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.infrastructure.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // JUnit에게 스프링을 사용한다고 알려준다.
@SpringBootTest // 스프링 컨테이너와 테스트를 함께 실행한다. (스프링 부트를 띄운 상태로 테스트를 진행한다.)
@Transactional // 테스트 케이스에 이 어노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고, 테스트 완료 후에 항상 롤백한다.
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    @Rollback(false) // 롤백을 하지 않고 커밋을 하게 된다.
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member); // 회원 가입
        
        //then
        em.flush(); // 영속성 컨텍스트에 있는 쿼리를 DB에 날린다. 날린 후에 실제 트랜잭션은 롤백한다.
        assertEquals(member, memberRepository.findOne(savedId));
    }
    
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");
        
        //when
        memberService.join(member1);
        memberService.join(member2); // 예외가 발생해야 한다.

        /**
         * try-catch를 사용하는 방법도 있지만, @Test(expected = IllegalStateException.class)를 사용하는 것이 더 깔끔하다.
         */
//        try {
//            memberService.join(member2); // 예외가 발생해야 한다.
//        } catch (IllegalStateException e) {
//            return;
//        }
        
        //then
        fail("예외가 발생해야 한다."); // 여기까지 오면 안된다.
    }
}