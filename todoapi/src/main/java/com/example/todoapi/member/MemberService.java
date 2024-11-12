package com.example.todoapi.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long createMember(String email, String password, String nickname) throws Exception {
        Member member = new Member(email, password, nickname);
        if (memberRepository.findByEmail(email) != null) {
            throw new Exception("Email already registered.");
        }
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public Member authenticate(String email, String password) throws Exception {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new Exception("Email does not exist.");
        }
        if (!member.getPassword().equals(password)) {
            throw new Exception("Wrong password.");
        }
        return member;
    }

    @Transactional
    public Member getMemberByEmail(String email) throws Exception {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new Exception("Member does not exist.");
        }
        return member;
    }

    @Transactional
    public List<Member> getMemberListByNickname(String nickname) {
        return memberRepository.findByNickname(nickname);
    }

    @Transactional
    public void changePassword(String email, String currentPassword, String newPassword) throws Exception {
        Member member = this.authenticate(email, currentPassword);
        member.updatePassword(newPassword);
    }

    @Transactional
    public void changeNickname(Long memberId, String newNickname) throws Exception {
        Member member = memberRepository.findById(memberId);
        if (member == null) {
            throw new Exception("Member not found.");
        }
        member.updateNickname(newNickname);
    }

    @Transactional
    public void deleteMember(String email, String password) throws Exception {
        Member member = this.authenticate(email, password);
        memberRepository.delete(member);
    }
}
