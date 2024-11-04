package com.example.todoapi.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void save(String email, String password, String nickname) throws Exception {
        Member member = new Member(email, password, nickname);
        if (memberRepository.findByEmail(email) != null) {
            throw new Exception("This email already registered.");
        }
        memberRepository.save(member);
    }

    @Transactional
    public boolean authenticate(String email, String password) {
        return memberRepository.findByEmailPw(email, password) == null;
    }

    @Transactional
    public void changePassword(Long memberId, String newPassword) throws Exception {
        Member member = memberRepository.findById(memberId);
        if (member == null) {
            throw new Exception("Member not found.");
        }
        member.updatePassword(newPassword);
    }

    @Transactional
    public void changeNickname(Long memberId, String newNickname) throws Exception {
        Member member = memberRepository.findById(memberId);
        if (member == null) {
            throw new Exception("Member not found.");
        }
        member.updatePassword(newNickname);
    }
}
