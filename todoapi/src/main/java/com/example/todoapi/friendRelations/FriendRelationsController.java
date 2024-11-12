package com.example.todoapi.friendRelations;

import com.example.todoapi.friendRelations.dto.FriendRelationRequest;
import com.example.todoapi.member.Member;
import com.example.todoapi.member.MemberController;
import com.example.todoapi.member.MemberService;
import com.example.todoapi.member.dto.MaskedMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends/")
public class FriendRelationsController {
    private final FriendRelationsService friendRelationsService;
    private final MemberController memberController;

    public MaskedMemberResponse getMaskedMember(Member member) {
        return memberController.getMaskedMember(member);
    }

    @GetMapping("list")
    public ResponseEntity<List<MaskedMemberResponse>> getFriendRelations(@RequestParam Long memberId) {
        List<Member> friends = friendRelationsService.getFriendList(memberId);
        List<MaskedMemberResponse> responses = friends.stream()
                .map(this::getMaskedMember)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFriendRelation(@RequestBody FriendRelationRequest request) throws Exception {
        friendRelationsService.deleteFriend(request.getMemberId(), request.getFriendId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("send")
    public ResponseEntity<Void> sendFriendRelation(@RequestBody FriendRelationRequest request) throws Exception {
        friendRelationsService.createFriendRelation(request.getMemberId(), request.getFriendId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("accept")
    public ResponseEntity<Void> acceptFriendRelation(@RequestBody FriendRelationRequest request) throws Exception {
        friendRelationsService.acceptFriendRequest(request.getMemberId(), request.getFriendId());
        return ResponseEntity.ok().build();
    }
}
