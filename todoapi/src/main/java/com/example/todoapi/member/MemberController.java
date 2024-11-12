package com.example.todoapi.member;

import com.example.todoapi.member.dto.MaskedMemberResponse;
import com.example.todoapi.member.dto.MemberCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    public MaskedMemberResponse getMaskedMember(Member member) {
        return new MaskedMemberResponse(member.getId(), member.getEmail(), member.getNickname());
    }

    @PostMapping("/login")
    ResponseEntity<MaskedMemberResponse> login(@RequestBody String email, String password) throws Exception {
        Member member = memberService.authenticate(email, password);
        return ResponseEntity.ok(getMaskedMember(member));
    }

    @PostMapping("/register")
    ResponseEntity<Long> register(@RequestBody MemberCreateRequest request) throws Exception {
        Long memberId = memberService.createMember(request.getEmail(), request.getPassword(), request.getNickname());
        return ResponseEntity.ok(memberId);
    }

    @DeleteMapping("/unregister")
    ResponseEntity<Void> unregister(@RequestBody String email, String password) throws Exception {
        memberService.deleteMember(email, password);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/modify/nickname")
    ResponseEntity<Void> modifyNickname(@RequestBody Long memberId, String newNickname) throws Exception {
        memberService.changeNickname(memberId, newNickname);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/modify/password")
    ResponseEntity<Void> modifyPassword(@RequestBody String email, String currentPassword, String newPassword) throws Exception {
        memberService.changePassword(email, currentPassword, newPassword);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/member/email")
    ResponseEntity<MaskedMemberResponse> getMemberByEmail(@RequestParam String email) throws Exception {
        Member member = memberService.getMemberByEmail(email);
        return ResponseEntity.ok(getMaskedMember(member));
    }

    @GetMapping("/member/nickname")
    ResponseEntity<List<MaskedMemberResponse>> getMembersByNicknames(@RequestParam String nickname) throws Exception {
        List<Member> member = memberService.getMemberListByNickname(nickname);
        List<MaskedMemberResponse> responses = member.stream()
                .map(this::getMaskedMember)
                .toList();
        return ResponseEntity.ok(responses);
    }
}
