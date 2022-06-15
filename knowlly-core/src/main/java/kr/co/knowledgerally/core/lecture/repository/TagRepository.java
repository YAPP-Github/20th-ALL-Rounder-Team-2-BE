package kr.co.knowledgerally.core.lecture.repository;

import kr.co.knowledgerally.core.lecture.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}