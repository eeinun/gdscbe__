package com.example.todoapi.member.dto;

import lombok.Getter;

@Getter
public class MemberCreateRequest {
    private String email;
    private String password;
    private String nickname;
}
