package com.jujuwon.book.springboot.web.dto;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void lombok_test() {
        // given
        String name = "test";
        int amount = 1000;

        // when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        // then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
        // assertThat : 테스트 검증 메소드
            // 검증하고 싶은 대상을 메소드 인자로 받음
            // 메소드 체이닝 지원 -> isEqualTo 와 같이 메소드 이어서 사용가능
    }
}
