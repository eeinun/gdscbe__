package com.example.todoapi.todo;

import com.example.todoapi.friendRelations.FriendRelationsRepository;
import com.example.todoapi.member.Member;
import com.example.todoapi.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;
    private final FriendRelationsRepository friendRelationsRepository;

    @Transactional
    public void createTodo(String content, Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId);
        if (member == null) {
            throw new Exception("Member not exists.");
        }
        Todo todo = new Todo(content, member);
        todoRepository.save(todo);
    }

    // 내 할 일, 친구의 할 일 조회
    @Transactional(readOnly = true)
    public List<Todo> getTodoList(Long memberId, Long requestedMemberId) throws Exception{
        Member member = memberRepository.findById(memberId);
        Member requestedMember = memberRepository.findById(requestedMemberId);
        if (member == null) {
            throw new Exception("Member not exists.");
        }
        if (!requestedMemberId.equals(memberId)) {
            if (!friendRelationsRepository.hasFriendRelation(member, requestedMember)) {
                throw new Exception("Access denied.");
            }
        }
        return todoRepository.findAllByMember(member);
    }

    @Transactional
    public void updateTodo(Long todoId, Long memberId, String updateContent) throws Exception{
        Member member = memberRepository.findById(memberId);
        Todo todo = todoRepository.findById(todoId);
        if (member == null) {
            throw new Exception("Member not exists.");
        }
        if (todo == null) {
            throw new Exception("Todo not exists.");
        }
        if (todo.getMember() != member) {
            throw new Exception("Access denied.");
        }
        todo.updateContent(updateContent);
    }

    @Transactional
    public void toggleTodo(Long todoId) throws Exception{
        Todo todo = todoRepository.findById(todoId);
        if (todo == null) {
            throw new Exception("Invalid todo ID.");
        }
        todo.toggleTodo();
    }

    @Transactional
    public void deleteTodo(Long memberId, Long todoId) throws Exception {
        Todo todo = todoRepository.findById(todoId);
        Member member = memberRepository.findById(memberId);
        if (todo.getMember() != member) {
            throw new Exception("Access denied.");
        }
        todoRepository.deleteById(todoId);
    }
}
