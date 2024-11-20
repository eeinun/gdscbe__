//package com.example.todoapi.todo;
//
//import com.example.todoapi.member.Member;
//import com.example.todoapi.member.MemberRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.BDDMockito;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.mockito.ArgumentMatchers.anyLong;
//
//@ExtendWith(MockitoExtension.class)
//public class TodoServiceTest {
//    @Mock
//    MemberRepository memberRepository;
//
//    @Mock
//    TodoRepository todoRepository;
//
//    @InjectMocks
//    TodoService todoService;
//
//    @Test
//    public void createTodoTest() throws Exception{
//        // given (precondition)
//        BDDMockito.given(memberRepository.findById(anyLong())).willReturn(new Member());
//
//        // when (run)
//        todoService.createTodo("content", 1L);
//        // then (is intended?)
//        /* todoRepository에 Todo 클래스의 무언가가 넘어왔다. */
//        Mockito.verify(todoRepository, Mockito.times(1)).save(Mockito.any(Todo.class));
//    }
//
//    @Test
//    public void createTodo() throws Exception {
//        BDDMockito.given(memberRepository.findById(1L)).willReturn(new Member());
//        Assertions.assertThatThrownBy(() -> {
//            todoService.createTodo("content", 123456L);
//        }).hasMessageContaining("Member not exists").isInstanceOf(Exception.class);
//        todoService.createTodo("content", 123456L);
//        Mockito.verify(todoRepository, Mockito.times(1)).save(Mockito.any(Todo.class));
//    }
//}