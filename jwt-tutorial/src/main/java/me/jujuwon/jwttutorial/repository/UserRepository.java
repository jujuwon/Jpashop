package me.jujuwon.jwttutorial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import me.jujuwon.jwttutorial.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	// username 을 기준으로 User 정보를 가져올 때 권한 정보도 같이 가져오는 메소드
	// EntityGraph 를 이용해 Lazy 로딩이 아닌 Eager 로딩이 되도록 설정
	@EntityGraph(attributePaths = "authorities")
	Optional<User> findOneWithAuthoritiesByUsername(String username);
}
