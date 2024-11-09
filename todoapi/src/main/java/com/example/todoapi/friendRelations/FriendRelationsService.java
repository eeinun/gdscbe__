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

    @Transactional
    public void createFriendRelation(Long memberId1, Long memberId2) throws Exception {
        FriendRelations fr = this.getFriendRelationByMemberId(memberId1, memberId2);
        if (fr.getState() == FriendRelations.State.PENDING) {
            throw new Exception("Friend request is pending.");
        }
        if (fr.getState() == FriendRelations.State.ACCEPTED) {
            throw new Exception("Friend request is already accepted.");
        }
        if (fr.getState() == FriendRelations.State.BLOCKED) {
            throw new Exception("Friend request is blocked.");
        }
        FriendRelations frReverse = this.getFriendRelationByMemberId(memberId2, memberId1);
        if (frReverse != null) {
            throw new Exception("Friend request already has been made from other side.");
        }
        friendRelationsRepository.save(fr);
    }

    @Transactional
    public FriendRelations getFriendRelationByMemberId(Long memberId1, Long memberId2) throws Exception {
        if (memberId1.equals(memberId2)) {
            throw new Exception("Member id is same.");
        }
        Member member1 = memberRepository.findById(memberId1);
        Member member2 = memberRepository.findById(memberId2);
        if (member1 == null || member2 == null) {
            throw new Exception("Some members not exist.");
        }
        FriendRelations fr = friendRelationsRepository.findFriendRelationByMembers(member1, member2);
        FriendRelations frReverse = friendRelationsRepository.findFriendRelationByMembers(member2, member1);
        if (fr != null) {
            return fr;
        }
        if (frReverse != null) {
            return frReverse;
        }
        throw new Exception("No friend relation found.");
    }

    @Transactional
    public List<Member> getFriendList(Long memberId) {
        Member member = memberRepository.findById(memberId);
        HashSet<Member> friendSet = new HashSet<>();
        List<FriendRelations> rel = friendRelationsRepository.findAllFriendRelationsByMember(member);
        for (FriendRelations fr : rel) {
            if (fr.getState() != FriendRelations.State.ACCEPTED) {
                continue;
            }
            if (!fr.getMember1().equals(member)) {
                friendSet.add(fr.getMember2());
            } else {
                friendSet.add(fr.getMember1());
            }
        }
        return new ArrayList<>(friendSet);
    }

    @Transactional
    public boolean hasFriendRelation(Long memberId1, Long memberId2) throws Exception {
        FriendRelations fr1 = this.getFriendRelationByMemberId(memberId1, memberId2);
        if (fr1 != null && fr1.getState() == FriendRelations.State.ACCEPTED) {
            return true;
        }
        FriendRelations fr2 = this.getFriendRelationByMemberId(memberId1, memberId2);
        return fr2 != null && fr2.getState() == FriendRelations.State.ACCEPTED;
    }

    @Transactional
    public List<FriendRelations> getPendingInboundFriendRequest(Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId);
        return friendRelationsRepository.findInboundFriendRelationsWithState(member, FriendRelations.State.PENDING);
    }

    @Transactional
    public List<FriendRelations> getBlockedInboundFriendRequest(Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId);
        return friendRelationsRepository.findInboundFriendRelationsWithState(member, FriendRelations.State.BLOCKED);
    }

    @Transactional
    public List<FriendRelations> getPendingOutboundFriendRequest(Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId);
        return friendRelationsRepository.findOutboundFriendRelationsWithState(member, FriendRelations.State.PENDING);
    }

    @Transactional
    public List<FriendRelations> getBlockedOutboundFriendRequest(Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId);
        return friendRelationsRepository.findOutboundFriendRelationsWithState(member, FriendRelations.State.BLOCKED);
    }

    @Transactional
    public void deleteFriend(Long memberId1, Long memberId2) throws Exception {
        FriendRelations fr = this.getFriendRelationByMemberId(memberId1, memberId2);
        if (fr.getState() == FriendRelations.State.BLOCKED && fr.getMember1().equals(memberRepository.findById(memberId1))) {
            throw new Exception("Can't delete blocked relationship by self.");
        }
        friendRelationsRepository.deleteFriendRelation(fr);
    }
}
