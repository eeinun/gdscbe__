package com.example.todoapi.todo;

import com.example.todoapi.friendRelations.FriendRelationsRepository;
import com.example.todoapi.friendRelations.FriendRelationsService;
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
    public Long createTodo(String content, Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId);
        if (member == null) {
            throw new Exception("Member not exists.");
        }
        Todo todo = new Todo(content, member);
        return todoRepository.save(todo);
    }

    // 내 할 일, 친구의 할 일 조회
    @Transactional(readOnly = true)
    public List<Todo> getTodoList(Long memberId, Long requestedMemberId) throws Exception {
        FriendRelationsService friendRelationsService = new FriendRelationsService();
        if (requestedMemberId.equals(memberId) || friendRelationsService.hasFriendRelation(memberId, requestedMemberId)) {
            return todoRepository.findAllByMember(memberRepository.findById(memberId));
        }
        throw new Exception("Access denied.");
    }

    @Transactional
    public boolean isEditable(Long todoId, Long memberId) throws Exception {
        Todo todo = todoRepository.findById(todoId);
        if (todo == null) {
            throw new Exception("Todo not exists");
        }
        Member member = memberRepository.findById(memberId);
        if (member == null) {
            throw new Exception("Member not exists");
        }
        return todo.getMember().equals(member);
    }

    @Transactional
    public void updateContent(Long todoId, Long memberId, String updateContent) throws Exception {
        if (updateContent.length() > Todo.CONTENT_MAXLENGTH) {
            throw new Exception("Content length over " + Todo.CONTENT_MAXLENGTH);
        }
        if (this.isEditable(todoId, memberId)) {
            todoRepository.findById(todoId).updateContent(updateContent);
        } else {
            throw new Exception("Access Denied");
        }
    }

    @Transactional
    public Boolean toggleIsChecked(Long todoId, Long memberId) throws Exception {
        if (this.isEditable(todoId, memberId)) {
            Todo todo = todoRepository.findById(todoId);
            todo.updateChecked(!todo.isChecked());
            return todo.isChecked();
        } else {
            throw new Exception("Access Denied");
        }
    }

    @Transactional
    public void deleteTodo(Long memberId, Long todoId) throws Exception {
        if (this.isEditable(todoId, memberId)) {
            todoRepository.deleteById(todoId);
        }
        throw new Exception("Access denied.");
    }
}
