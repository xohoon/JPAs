package xohoon.study.JPAs.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import xohoon.study.JPAs.domain.Member;
import xohoon.study.JPAs.repository.MemberRepository;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest // 스프링 부트 띄우고 테스트 할 때 필요
@Transactional // 테스트 완료 후 롤백
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
//  @Rollback(false) // 롤백 false
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        em.flush();; // rollback전에 query 보고싶을때
        assertEquals(member, memberRepository.findOne((saveId)));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("name");

        Member member2 = new Member();
        member2.setName("name");

        //when
        memberService.join(member1);
        memberService.join(member2); // 여기서 예외 발생 해야함

        /*
        * @Test에 exception 넣음으로서 생략 가능
        try {
            memberService.join(member2); // 여기서 예외 발생 해야함
        }catch (IllegalStateException e) {
            return;
        }
        * */

        //then
        fail("예외가 발생해야 한다.");
    }

}