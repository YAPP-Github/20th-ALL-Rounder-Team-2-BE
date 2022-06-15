package kr.co.knowledgerally.core.user.repository;

import kr.co.knowledgerally.core.user.entity.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {
}