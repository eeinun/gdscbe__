package com.example.todoapi.todo;

import com.example.todoapi.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@NoArgsConstructor
public class TodoRepository {

    @PersistenceContext
    private EntityManager em;

    // Create
    public void save(Todo todo) {
        em.persist(todo); // put todo object in persistant context
    }

    // Read one
    public Todo findById(Long id) {
        return em.find(Todo.class, id);
    }

    // Read all
    public List<Todo> findAll() {
        return em.createQuery("select t from Todo as t", Todo.class).getResultList();
    }

    // Read conditional
    public List<Todo> findAllByMember(Member member) {

        return em.createQuery("select t from Todo as t where t.member = :member", Todo.class)
                .setParameter("member", member)
                .getResultList();
    }

    // Update: Entity class does

    // Delete
    public void deleteById(Long id) {
        em.remove(findById(id));
    }


    public void flushAndClear() {
        em.flush();
        em.clear();
    }
}
