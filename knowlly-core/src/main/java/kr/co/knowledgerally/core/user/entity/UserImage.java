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
@Table(name = "user_image")
public class UserImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @MapsId("id")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String userImgUrl;

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
