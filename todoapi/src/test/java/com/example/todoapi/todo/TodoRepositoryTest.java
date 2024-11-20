package com.example.todoapi.todo;

import com.example.todoapi.member.Member;
import com.example.todoapi.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TodoRepositoryTest {
    @Autowired
    private TodoRepository todoRepository;
    private MemberRepository memberRepository;

//    @Test
//    @Transactional
//    @Rollback(false)
//    void todoSaveTest() {
//        Todo todo = new Todo("Todo content", false, null);
//        todoRepository.save(todo);
//
//        Assertions.assertThat(todo.getId()).isNotNull();
//    }

//    @Test
//    @Transactional
//    void todoFindOneByIdTest() {
//        Todo todo = new Todo("Todo content", false, null);
//
//        todoRepository.save(todo); // given
//        Todo findTodo = todoRepository.findById(todo.getId()); // when
//        Assertions.assertThat(findTodo.getId()).isEqualTo((todo.getId())); // then
//    }

//    @Test
//    @Transactional
//    void todoFindAllByIdTest() {
//        Todo todo1 = new Todo("Todo content1", false, null);
//        Todo todo2 = new Todo("Todo content2", false, null);
//        Todo todo3 = new Todo("Todo content3", false, null);
//        todoRepository.save(todo1);
//        todoRepository.save(todo2);
//        todoRepository.save(todo3);
//
//        List<Todo> todoList = todoRepository.findAll();
//
//        Assertions.assertThat(todoList).hasSize(3);
//    }

//    @Test
//    @Transactional
//    void todoFindAllByMember() {
//        Member member1 = new Member();
//        Member member2 = new Member();
//        memberRepository.save(member1);
//        memberRepository.save(member2);
//
//        Todo todo1 = new Todo("Todo content1", member1);
//        Todo todo2 = new Todo("Todo content2", member1);
//        Todo todo3 = new Todo("Todo content3", member2);
//        todoRepository.save(todo1);
//        todoRepository.save(todo2);
//        todoRepository.save(todo3);
//
//        List<Todo> member1TodoList = todoRepository.findAllByMember(member1);
//        List<Todo> member2TodoList = todoRepository.findAllByMember(member2);
//
//        Assertions.assertThat(member1TodoList).hasSize(2);
//        Assertions.assertThat(member2TodoList).hasSize(1);
//
//    }

//    @Test
//    @Transactional
//    void todoUpdateTest() {
//        Todo todo1 = new Todo("Todo content1", false, null);
//        todoRepository.save(todo1);
//        todoRepository.flushAndClear();
//
//        Todo findTodo1 = todoRepository.findById(todo1.getId());
//        findTodo1.updateContent("New content");
//    }

//    @Test
//    @Transactional
//    @Rollback(false)
//    void todoUpdateTest() {
//        Todo todo1 = new Todo("Todo content1", null);
//        Todo todo2 = new Todo("Todo content2", null);
//        todoRepository.save(todo1);
//        todoRepository.save(todo2);
//        todoRepository.flushAndClear();
//        todoRepository.deleteById(todo1.getId());
//    }

//    @AfterAll
//    public static void donNotFinish() {
//        System.out.println("Test finished");
//        while(true) {}
//    }
}
