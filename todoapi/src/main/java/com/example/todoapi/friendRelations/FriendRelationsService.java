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
        if (memberId1 == null || memberId2 == null) {
            throw new Exception("Some member id is null.");
        }
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
    public List<Member> getPendingInboundFriendRequest(Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId);
        List<FriendRelations> pending = friendRelationsRepository.findInboundFriendRelationsWithState(member, FriendRelations.State.PENDING);
        List<Member> requesters = new ArrayList<>();
        for (FriendRelations fr : pending) { requesters.add(fr.getMember1()); }
        return requesters;
    }

    @Transactional
    public List<Member> getBlockedInboundFriendRequest(Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId);
        List<FriendRelations> blocked = friendRelationsRepository.findInboundFriendRelationsWithState(member, FriendRelations.State.BLOCKED);
        List<Member> requesters = new ArrayList<>();
        for (FriendRelations fr : blocked) { requesters.add(fr.getMember1()); }
        return requesters;
    }

    @Transactional
    public List<Member> getPendingOutboundFriendRequest(Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId);
        List<FriendRelations> pending = friendRelationsRepository.findOutboundFriendRelationsWithState(member, FriendRelations.State.PENDING);
        List<Member> requesters = new ArrayList<>();
        for (FriendRelations fr : pending) { requesters.add(fr.getMember1()); }
        return requesters;
    }

    @Transactional
    public void acceptFriendRequest(Long memberId1, Long memberId2) throws Exception {
        Member member1 = memberRepository.findById(memberId1);
        Member member2 = memberRepository.findById(memberId2);
        FriendRelations fr = friendRelationsRepository.findFriendRelationByMembers(member2, member1);
        if (fr == null) { throw new Exception("Friend relation is not acceptable or not exists."); }
        fr.updateState(FriendRelations.State.ACCEPTED);
    }

    @Transactional
    public void blockFriendRequest(Long memberId1, Long memberId2) throws Exception {
        if (!this.hasFriendRelation(memberId1, memberId2)) {
            throw new Exception("FriendRelation not exists.");
        }
        Member member1 = memberRepository.findById(memberId1);
        Member member2 = memberRepository.findById(memberId2);
        FriendRelations fr = this.getFriendRelationByMemberId(memberId1, memberId2);
        // 차단당한 상대는 해제할 수 없어야하고 member1은 차단할 수 없게 돼있으므로 맞춰서
        if (fr.getMember1() == member1 && fr.getMember2() == member2) {
            friendRelationsRepository.deleteFriendRelation(fr);
            friendRelationsRepository.save(new FriendRelations(member2, member1));
        }
        else {
            fr.updateState(FriendRelations.State.BLOCKED);
        }
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
