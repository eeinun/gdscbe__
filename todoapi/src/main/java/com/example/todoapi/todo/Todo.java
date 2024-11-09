package com.example.todoapi.todo;

import com.example.todoapi.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo {
    public static final Integer CONTENT_MAXLENGTH = 2000;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "content", columnDefinition = "VARCHAR(2000)")
    private String content;

    @Column(name = "isChecked", columnDefinition = "BOOLEAN")
    private boolean isChecked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private Member member;

    public Todo(String content, Member member) {
        this.content = content;
        this.isChecked = false;
        this.member = member;
    }

    public void updateContent(String newContent) {
        this.content = newContent;
    }

    public void updateChecked(boolean isChecked) {
        this.isChecked = !this.isChecked;
    }
}
