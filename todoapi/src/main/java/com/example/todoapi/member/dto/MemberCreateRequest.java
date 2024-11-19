package com.example.todoapi.member.dto;
import com.example.todoapi.common.message.ErrorMessage;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class MemberCreateRequest {
    @NotNull(message = "이메일 " + ErrorMessage.VALUE_IS_NOT_GIVEN)
    @Length(min = 1, max = 320, message = "이메일 " + ErrorMessage.LENGTH_OUT_OF_RANGE + "[1, 320]")
    @Pattern(regexp = "^[^@]+@([\\w\\d]+\\.)+[\\w\\d]+$", message = ErrorMessage.EMAIL_FORMAT_ERROR)
    private String email;
    @NotNull(message = "비밀번호 " + ErrorMessage.VALUE_IS_NOT_GIVEN)
    @Length(min = 10, max = 100, message = "비밀번호 " + ErrorMessage.LENGTH_OUT_OF_RANGE + "[10, 100]")
    private String password;
    @NotNull(message = "닉네임 " + ErrorMessage.VALUE_IS_NOT_GIVEN)
    @Length(min = 1, max = 10, message = "닉네임 " + ErrorMessage.LENGTH_OUT_OF_RANGE + "[1, 20]")
    private String nickname;
}
