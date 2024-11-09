package com.example.todoapi.todo;

import com.example.todoapi.member.Member;
import com.example.todoapi.member.MemberRepository;
import com.example.todoapi.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    public void saveTest_Normal() throws Exception {
        BDDMockito.willDoNothing().given(memberRepository).save(any());
        memberService.createMember("T", "T", "T");
        BDDMockito.verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    public void saveTest_AlreadyRegisteredEmail() throws Exception {
        BDDMockito.given(memberRepository.findByEmail(any())).willReturn(new Member());
        Assertions.assertThatThrownBy(() -> memberService.createMember("T", "T", "T"))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Email already registered");
    }

    @Test
    public void authenticateTest() throws Exception {
        Member member = new Member("T", "T", "T");
        BDDMockito.given(memberRepository.findByEmail(any())).willReturn(member);
        Member loggedInMember = memberService.authenticate("T", "T");
        BDDMockito.verify(memberRepository, times(1)).findByEmail("T");
        Assertions.assertThat(loggedInMember).isNotNull();
    }

    @Test
    public void changePassword_IncorrectPassword() throws Exception {
        Member member = new Member("T", "T", "T");
        BDDMockito.given(memberRepository.findByEmail(any())).willReturn(member);
        Assertions.assertThatThrownBy(() -> memberService.changePassword("T", "wrong_password", "T"))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Wrong password");
    }
    @Test
    public void changePassword_IncorrectEmail() throws Exception {
        Member member = new Member("T", "T", "T");
        BDDMockito.given(memberRepository.findByEmail(any())).willReturn(null);
        Assertions.assertThatThrownBy(() -> memberService.changePassword("T", "wrong_password", "T"))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Email does not exist.");
    }

    @Test
    public void changeNickname_NoMember() throws Exception {
        Assertions.assertThatThrownBy(() -> memberService.changeNickname(1L, "T"))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Member not found");
    }
}
