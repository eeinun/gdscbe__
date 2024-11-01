package com.example.todoapi.friendRelations;

import com.example.todoapi.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FriendRelationsRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(FriendRelations fr) {
        em.persist(fr);
    }

    public List<FriendRelations> findEveryFriendRelation(Member member) {
        return em.createQuery("select t from FriendRelations t where t.member = :member or t.friend = :member", FriendRelations.class)
                .setParameter("member", member)
                .getResultList();
    }

    public Boolean hasFriendRelation(Member member1, Member member2) {
        String query = "select t from FriendRelations t where (t.member = :member1 and t.friend = :member2) or (t.member = :member2 and t.friend = :member1)";
        List<FriendRelations> friendship = em.createQuery(query, FriendRelations.class)
                .setParameter("member1", member1)
                .setParameter("member2", member2)
                .getResultList();
        return !friendship.isEmpty();
    }

    public void removeFriendRelation(FriendRelations fr) {
        em.remove(fr);
    }
}
