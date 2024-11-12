package com.example.todoapi.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@NoArgsConstructor
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member findById(long id) {
        return em.find(Member.class, id);
    }

    public Member findByEmail(String email) {
        List<Member> result = em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    public List<Member> findByNickname(String email) {
        return em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList();
    }

    public void delete(Member member) {
        em.remove(member);
    }

    public void DeleteById(long id) {
        em.remove(em.find(Member.class, id));
    }
}
