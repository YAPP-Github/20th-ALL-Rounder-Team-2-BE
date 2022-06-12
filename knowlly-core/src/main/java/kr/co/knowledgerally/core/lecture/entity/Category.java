package kr.co.knowledgerally.core.lecture.entity;

import lombok.*;

import javax.persistence.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String categoryName;

    @Builder.Default
    @Column(nullable = false)
    private boolean isActive = true;
}
