package com.example.todoapi.common.message;

public class ErrorMessage {
    // common error
    public static final String LENGTH_OUT_OF_RANGE = "길이가 범위를 벗어났습니다. 범위: ";
    public static final String VALUE_IS_NOT_GIVEN = "값이 주어지지 않았습니다.";

    // email error
    public static final String EMAIL_FORMAT_ERROR = "이메일 형식이 올바르지 않습니다.";
    public static final String EMAIL_ALREADY_EXISTS = "이미 가입된 이메일 주소입니다.";
    public static final String EMAIL_NOT_EXISTS = "존재하지 않는 이메일 주소입니다.";

    // password error
    public static final String INCORRECT_PASSWORD = "비밀번호가 틀렸습니다.";
}
