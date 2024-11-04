package com.example.todoapi.friendRelations;

import com.example.todoapi.member.Member;
import com.example.todoapi.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendRelationsService {
    FriendRelationsRepository friendRelationsRepository;
    MemberRepository memberRepository;

    // 친구 추가
    @Transactional
    public void addFriend(Long memberId1, Long memberId2) throws Exception {
        Member member1 = memberRepository.findById(memberId1);
        Member member2 = memberRepository.findById(memberId2);
        if (member1 == null || member2 == null) {
            throw new Exception("Some members not exist.");
        }
        if (friendRelationsRepository.hasFriendRelation(member1, member2)) {
            throw new Exception("Already friend.");
        }
        FriendRelations friendRelations = new FriendRelations(member1, member2);
        friendRelationsRepository.save(friendRelations);
    }

    // 모든 친구 조회
    @Transactional
    public List<Member> getFriends(Long memberId) {
        Member member = memberRepository.findById(memberId);
        HashSet<Member> friends = new HashSet<>();
        List<FriendRelations> rel = friendRelationsRepository.findEveryFriendRelations(member);
        for (FriendRelations friend : rel) {
            if (!friend.getMember().equals(member)) { friends.add(friend.getMember()); }
            if (!friend.getFriend().equals(member)) { friends.add(friend.getFriend()); }
        }
        return new ArrayList<>(friends);
    }

    // 친구맞음?
    @Transactional
    public boolean isFriend(Long memberId1, Long memberId2) throws Exception {
        Member member1 = memberRepository.findById(memberId1);
        Member member2 = memberRepository.findById(memberId2);
        if (member1 == null || member2 == null) {
            throw new Exception("Some members not exist.");
        }
        return friendRelationsRepository.hasFriendRelation(member1, member2);
    }

    // 친구 삭제
    @Transactional
    public void removeFriend(Long memberId1, Long memberId2) throws Exception {
        Member member1 = memberRepository.findById(memberId1);
        Member member2 = memberRepository.findById(memberId2);
        if (member1 == null || member2 == null) {
            throw new Exception("Some members not exist.");
        }
        FriendRelations fr = friendRelationsRepository.getStrictRelation(member1, member2);
        friendRelationsRepository.removeFriendRelation(fr);
    }
}
