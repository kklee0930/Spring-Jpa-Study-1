package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.infrastructure.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // Test에 끝난 후에 DB를 롤백한다.
//@Rollback(false) // Transactional을 통한 롤백을 하지 않는다.
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    
    @Test
    public void testMember() throws Exception {

//        //given
//        Member member = new Member();
//        member.setUsername("memberA");
//
//        //when
//        Long savedId = memberRepository.save(member);
//        Member findMember = memberRepository.find(savedId);
//
//        //then
////        Member findMember = memberRepository.find(savedId);
//        assertEquals(findMember.getId(), member.getId());
//        assertEquals(findMember.getUsername(), member.getUsername());
//        assertEquals(findMember, member);
//        System.out.println(findMember == member);
    }
}