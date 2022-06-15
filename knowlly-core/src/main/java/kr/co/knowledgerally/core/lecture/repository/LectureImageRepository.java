package kr.co.knowledgerally.core.lecture.repository;

import kr.co.knowledgerally.core.lecture.entity.LectureImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureImageRepository extends JpaRepository<LectureImage, Long> {
}