package kr.co.knowledgerally.core.lecture.repository;

import kr.co.knowledgerally.core.lecture.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
}