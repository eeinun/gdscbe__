package com.example.todoapi.todo;

import com.example.todoapi.todo.dto.TodoCreateRequest;
import com.example.todoapi.todo.dto.TodoUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/todo/")
public class TodoController {
    private final TodoService todoService;

    // request --> json --> DTO --> 처리 --> ResponseEntity 로 응답 생성
    @PostMapping
    public ResponseEntity<Void> createTodo(@RequestBody TodoCreateRequest request) throws Exception {
        Long todoId = todoService.createTodo(request.getContent(), request.getUid());
        return ResponseEntity.created(URI.create("/todo/" + todoId)).build();
    }

    @GetMapping("list")
    public ResponseEntity<List<Todo>> getTodoList(@RequestBody Long memberId) throws Exception {
        List<Todo> tl = todoService.getTodoList(memberId, memberId);
        return ResponseEntity.ok(tl);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long todoId, @RequestBody Long memberId) throws Exception {
        todoService.deleteTodo(todoId, memberId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{todoId}")
    public ResponseEntity<Void> updateTodo(@PathVariable Long todoId, @RequestBody TodoUpdateRequest request) throws Exception {
        todoService.updateContent(todoId, request.getMemberId(), request.getContent());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{todoId}/toggle")
    public ResponseEntity<Boolean> toggleTodo(@PathVariable Long todoId, @RequestBody Long memberId) throws Exception {
        return ResponseEntity.ok(todoService.toggleIsChecked(todoId, memberId));
    }
}