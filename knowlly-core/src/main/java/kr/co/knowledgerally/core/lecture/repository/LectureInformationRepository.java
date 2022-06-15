package kr.co.knowledgerally.core.lecture.repository;

import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureInformationRepository extends JpaRepository<LectureInformation, Long> {
}