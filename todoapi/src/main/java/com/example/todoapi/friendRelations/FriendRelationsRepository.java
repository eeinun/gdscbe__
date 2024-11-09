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

    public List<FriendRelations> findAllFriendRelationsByMember(Member member) {
        return em.createQuery("select t from FriendRelations t where t.member1 = :member or t.member2 = :member", FriendRelations.class)
                .setParameter("member", member)
                .getResultList();
    }

    public FriendRelations findFriendRelationByMembers(Member member1, Member member2) {
        String query = "select t from FriendRelations t where (t.member1 = :member1 and t.member2 = :member2)";
        FriendRelations fr = em.createQuery(query, FriendRelations.class)
                .setParameter("member1", member1)
                .setParameter("member2", member2)
                .getSingleResult();
        return fr;
    }

    public List<FriendRelations> findOutboundFriendRelationsWithState(Member member, FriendRelations.State state) {
        String query = "select t from FriendRelations t where (t.member1 = :member and t.state = :state)";
        return em.createQuery(query, FriendRelations.class)
                .setParameter("member", member)
                .setParameter("state", state)
                .getResultList();
    }

    public List<FriendRelations> findInboundFriendRelationsWithState(Member member, FriendRelations.State state) {
        String query = "select t from FriendRelations t where (t.member2 = :member and t.state = :state)";
        return em.createQuery(query, FriendRelations.class)
                .setParameter("member", member)
                .setParameter("state", state)
                .getResultList();
    }

    public void deleteFriendRelation(FriendRelations fr) {
        em.remove(fr);
    }
}
