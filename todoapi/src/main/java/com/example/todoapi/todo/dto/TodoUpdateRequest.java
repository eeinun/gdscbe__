package com.example.todoapi.todo.dto;

import lombok.Getter;

@Getter
public class TodoUpdateRequest {
    private Long memberId;
    private String content;
}
