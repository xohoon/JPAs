package xohoon.study.JPAs.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xohoon.study.JPAs.domain.Member;
import xohoon.study.JPAs.repository.MemberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true) // 읽기 전용 성능 최적화 -> 읽기 가 많으면 true를 전체에 먹이고 아니면 기본으로 놔두면 됨
// @AllArgsConstructor -> 생성자 만들어줌
@RequiredArgsConstructor // -> final 있는 필드만 생성자 만들어줌
public class MemberService {

    private final MemberRepository memberRepository;

    /*
    * @RequiredArgsConstructor 사용으로 생략 가능
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    * */

    /*
    * 회원 가입
    * */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);

        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /*
    * 회원 조회
    * */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }
}
