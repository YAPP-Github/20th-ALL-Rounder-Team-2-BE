package kr.co.knowledgerally.core.user.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private int ballCnt;

    @Column(nullable = false)
    private String intro;

    @Column(nullable = false)
    private String kakaoId;

    @Column(nullable = false)
    private String portfolio;

    @Column(nullable = false)
    private String identifier;

    @Column(nullable = false)
    @Builder.Default
    private boolean isCoach = false;

    @Column(nullable = false)
    @Builder.Default
    private boolean isPushActive = true;

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
