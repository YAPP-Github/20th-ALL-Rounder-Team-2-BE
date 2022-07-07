package kr.co.knowledgerally.core.lecture.entity;

import kr.co.knowledgerally.core.coach.entity.Coach;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lecture_information")
public class LectureInformation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private Coach coach;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "lectureInformation", cascade = CascadeType.REMOVE)
    private Set<LectureImage> lectureImages;

    @OneToMany(mappedBy = "lectureInformation", cascade = CascadeType.REMOVE)
    private Set<Tag> tags;

    @Column(nullable = false)
    private String topic;

    @Column(nullable = false)
    private String introduce;

    @Builder.Default
    @Column(nullable = false)
    private int price = 1;

    @Builder.Default
    @Column(nullable = false)
    private boolean isActive = true;

    @Builder.Default
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
}
