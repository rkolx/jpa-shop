package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {


    // 생성자로 인젝션 자동으로 autowired 엔티티 메니저 인젝션 해줄 수 있음
    //@PersistenceContext
    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    //JPQL의 from은 테이블이 아닌 엔티티
    public List<Member> findAll(){
        return em.createQuery("select  m from Member  m", Member.class)
                .getResultList();
    }

    //이름에 의한 조회
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member  m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
