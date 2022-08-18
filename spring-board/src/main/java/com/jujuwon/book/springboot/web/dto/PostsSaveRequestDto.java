package com.jujuwon.book.springboot.web.dto;

import com.jujuwon.book.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    // Entity 클래스와 거의 유사한 형태이지만 Dto 클래스를 추가로 생성해서 사용해야 함
    // Entity 클래스를 Req/Res 클래스로 사용해서는 안 됨 -> DB와 맞닿은 핵심 클래스

    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
