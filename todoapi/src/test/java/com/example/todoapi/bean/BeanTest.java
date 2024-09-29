package com.example.todo_api.bean;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanTest {
    // ApplicationContext 는 인터페이스이므로 구현클래스 필요
    ApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test // 스프링 컨테이너 생성, 조회
    public void getAllBeansTest() {
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        // context 안에 myBean이 들어있는지 검증
        Assertions.assertThat(context.getBeanDefinitionNames()).contains("myBean");
    }

    @Test
    public void getOneBeanTest() {
        MyBean myBean1 = context.getBean(MyBean.class);
        MyBean myBean2 = context.getBean(MyBean.class);
        MyBean myBean3 = new MyBean();

        System.out.println(myBean1);
        System.out.println(myBean2);
        System.out.println(myBean3);

        // 성공
        Assertions.assertThat(myBean1).isSameAs(myBean2);
        // 실패
//        Assertions.assertThat(myBean1).isSameAs(myBean3);
    }

}
