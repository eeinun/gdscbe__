package com.example.todoapi.todo;

import com.example.todoapi.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "content", columnDefinition = "VARCHAR(2000)")
    private String content;

    @Column(name = "toggle", columnDefinition = "BOOLEAN")
    private boolean toggle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private Member member;

    public Todo(String content, boolean toggle, Member member) {
        this.content = content;
        this.toggle = toggle;
        this.member = member;
    }
}
