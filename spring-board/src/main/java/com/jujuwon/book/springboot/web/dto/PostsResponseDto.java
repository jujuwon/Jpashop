package com.jujuwon.book.springboot.web.dto;

import com.jujuwon.book.springboot.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDto(Posts entity) {
        // PostsResponseDto 는 Entity 필드 중 일부만 사용하므로 생성자로 Entity 를 받아 필드에 값 넣어줌
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
