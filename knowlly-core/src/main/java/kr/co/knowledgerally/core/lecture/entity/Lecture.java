package kr.co.knowledgerally.core.lecture.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lecture")
public class Lecture {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lecture_info_id")
    private LectureInformation lectureInformation;

    @Column(nullable = false)
    private LocalDateTime startAt;

    @Column(nullable = false)
    private LocalDateTime endAt;

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private State state = State.ON_BOARD;

    @Builder.Default
    @Column(nullable = false)
    private boolean isReviewWritten = false;

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

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL)
    private List<Form> forms;

    public enum State {
        ON_BOARD,
        ON_GOING,
        DONE
    }

    /**
     * ACCEPT 상태의 Form을 리턴하는 편의 메소드
     * @return State가 ACCEPT인 Form Optional을 리턴함, 없으면 Optional.null
     */
    public Optional<Form> getAcceptedForm() {
        return forms.stream().filter(x -> x.getState() == Form.State.ACCEPT).findFirst();
    }
}
