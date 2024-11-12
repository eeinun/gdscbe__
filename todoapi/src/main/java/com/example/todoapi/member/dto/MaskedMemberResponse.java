package com.example.todoapi.member.dto;

import com.example.todoapi.member.Member;
import lombok.Getter;

@Getter
public class MaskedMemberResponse {
    private Long memberId;
    private String email;
    private String nickname;

    public MaskedMemberResponse(Long id, String email, String nickname) {
        this.memberId = id;
        this.email = email;
        this.nickname = nickname;
    }
}
