package kr.co.knowledgerally.core.user.repository;

import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.entity.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {
    /**
     * 사용자로 이미지들을 찾는다.
     * @param user 사용자
     * @return 사용자 이미지 목록
     */
    List<UserImage> findAllByUser(User user);
}