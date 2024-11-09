package com.example.todoapi.friendRelations;

import com.example.todoapi.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class FriendRelations {
    public enum State {
        PENDING,
        ACCEPTED,
        BLOCKED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State state;

    @ManyToOne
    @JoinColumn(name = "member1")
    private Member member1;

    @ManyToOne
    @JoinColumn(name = "member2")
    private Member member2;

    public FriendRelations(Member member1, Member member2) {
        this.member1 = member1;
        this.member2 = member2;
        this.state = State.PENDING;
    }

    public void updateState(State state) {
        this.state = state;
    }
}
