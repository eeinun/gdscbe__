package com.example.todoapi.bean;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class MyBean {
    // 의존성 명시
    private MySubBean mySubBean;
}
