package com.example.todoapi.todo;

import com.example.todoapi.friendRelations.FriendRelations;
import com.example.todoapi.friendRelations.FriendRelationsRepository;
import com.example.todoapi.member.Member;
import com.example.todoapi.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FRRepositoryTest {
    @Autowired
    private FriendRelationsRepository fRRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    void FRRTest() {
        Member member1 = new Member();
        Member member2 = new Member();
        Member member3 = new Member();

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        FriendRelations fr1 = new FriendRelations(member1, member2);
        FriendRelations fr2 = new FriendRelations(member1, member3);

        // Create test
        fRRepository.save(fr1);
        fRRepository.save(fr2);

        // Read-many test
        Assertions.assertThat(fRRepository.findAllFriendRelationsByMember(member1)).hasSize(2);
        Assertions.assertThat(fRRepository.findAllFriendRelationsByMember(member2)).hasSize(1);
        Assertions.assertThat(fRRepository.findAllFriendRelationsByMember(member3)).hasSize(1);

        // Read-one test
        Assertions.assertThat(fRRepository.findFriendRelationByMembers(member1, member2) != null).isTrue();
        Assertions.assertThat(fRRepository.findFriendRelationByMembers(member1, member3) != null).isTrue();
        Assertions.assertThat(fRRepository.findFriendRelationByMembers(member3, member2) != null).isFalse();

        // Delete test
        fRRepository.deleteFriendRelation(fr1);
        Assertions.assertThat(fRRepository.findFriendRelationByMembers(member1, member2) != null).isFalse();
    }
}
