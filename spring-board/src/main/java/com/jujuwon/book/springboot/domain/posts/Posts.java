package com.jujuwon.book.springboot.domain.posts;

import com.jujuwon.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
// 테이블과 링크될 클래스임을 나타냄
// Default 값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍으로 테이블 이름 매칭
// ex) SalesManager.java -> sales_manager table
// Entity 클래스에는 Setter 메소드를 만들지 말 것 !
public class Posts extends BaseTimeEntity {

    @Id
    // 해당 테이블의 PK 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // PK 의 생성 규칙을 나타냄. Spring Boot 2.0 에서는 GenerationType.IDENTITY 옵션을 추가해야 auto_increment 가 됨
    private Long id;

    @Column(length = 500, nullable = false)
    // 테이블의 컬럼을 나타냄. 선언하지 않더라도 해당 클래스 필드는 모두 컬럼이 되지만, 변경이 필요한 옵션이 있을 때 사용
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    // 해당 클래스의 빌더 패턴 클래스 생성
    // 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    // 생성자나 빌더나 생성 시점에 값을 채워주는 역할은 똑같지만, 생성자는 지금 채워야 할 필드가 무엇인지 명확히 지정할 수 없다.
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
