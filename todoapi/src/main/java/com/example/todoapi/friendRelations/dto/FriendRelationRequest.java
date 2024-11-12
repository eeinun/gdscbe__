package com.example.todoapi.friendRelations.dto;

import lombok.Getter;

@Getter
public class FriendRelationRequest {
    private Long memberId;
    private Long friendId;
}
