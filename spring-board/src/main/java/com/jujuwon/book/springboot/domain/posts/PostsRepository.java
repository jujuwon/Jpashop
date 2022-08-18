package com.jujuwon.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    // Repository : SQL Mapper 에서 DAO 라고 불리는 DB Layer 접근자
    // 인터페이스 생성 후, JpaRepository<Entity 클래스, PK 타입>을 상속하면 기본적인 CRUD 메소드 자동 생성
    // @Repository 추가할 필요 X, Entity 클래스와 기본 EntityRepository 는 함께 위치해야함 (domain 패키지에서 함께 관리)
}
