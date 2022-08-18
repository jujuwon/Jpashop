package com.jujuwon.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
// 테스트를 진행할 때 JUnit 에 내장된 실행자 외 다른 실행자 실행
// 여기서는 SpringRunner 라는 스프링 실행자 사용 즉, Spring Boot 테스트와 JUnit 사이 연결자 역할
@WebMvcTest(controllers = HelloController.class)
// 여러 Spring 테스트 Annotation 중, Web(Spring MVC)에 집중할 수 있는 Annotation
public class HelloControllerTest {

    @Autowired // Spring 이 관리하는 Bean 주입
    private MockMvc mvc; // Web API 테스트 시 사용, Spring MVC 테스트의 시작점

    @Test
    public void helloTest() throws Exception {
        String hello = "hello";

        // MockMvc 를 통해 /hello 주소로 GET 요청
        // 체이닝 지원, 아래와 같이 여러 검증 기능 이어서 선언 가능
        mvc.perform(get("/hello"))
                .andExpect(status().isOk()) // perform 결과 검증, status 가 200인지 검증
                .andExpect(content().string(hello)); // 응답 본문의 내용 검증. Controller 에서 "hello" 를 리턴하기 때문에 해당 값 검증
    }

    @Test
    public void helloDtoTest() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
            get("/hello/dto")
                .param("name", name)
                .param("amount", String.valueOf(amount))
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(name)))
            .andExpect(jsonPath("$.amount", is(amount)));
        // jsonPath : JSON 응답값을 필드별로 검증할 수 있는 메소드
        // $를 기준으로 필드명을 명시
        // 여기서는 name 과 amount 를 검증하니 $.name, $.amount 로 검증
    }
}
