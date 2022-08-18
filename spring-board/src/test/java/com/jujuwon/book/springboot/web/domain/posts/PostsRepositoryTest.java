package com.jujuwon.book.springboot.web.domain.posts;

import com.jujuwon.book.springboot.domain.posts.Posts;
import com.jujuwon.book.springboot.domain.posts.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    // Junit 에서 단위 테스트가 끝날 때마다 수행되는 메소드 지정
    // 보통은 배포 전 전체 테스트를 수행할 때 테스트 간 데이터 침범을 막기 위해 사용
    // 여러 테스트가 동시에 수행되면 테스트용 DB 인 H2 에 데이터가 그대로 남아 있어 다음 테스트 실행 시 테스트가 실패할 수 있음
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void load_save_post_test() {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        // 테이블 posts 에 insert/update 쿼리 실행
        // id 값이 있다면 update, 없다면 insert 쿼리가 실행됨
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("webmon@kakao.com")
                .build());

        // when
        // 테이블 posts 에 있는 모든 데이터를 조회해오는 메소드
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }
}
