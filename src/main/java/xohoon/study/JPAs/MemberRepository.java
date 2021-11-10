package xohoon.study.JPAs;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
//  Spring Boot는 entity manager 기본 세팅 다해줌
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);

        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
