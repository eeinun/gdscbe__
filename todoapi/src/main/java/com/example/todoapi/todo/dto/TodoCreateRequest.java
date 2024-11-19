package com.example.todoapi.todo.dto;

import com.example.todoapi.common.message.ErrorMessage;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class TodoCreateRequest {
    @Length(min = 1, max = 2000, message = ErrorMessage.LENGTH_OUT_OF_RANGE + "[1, 2000]") //  범위도 클래스로...
    private String content;
    @NotNull(message = "유저 아이디가 주어져야 합니다.")
    private Long uid;
}
