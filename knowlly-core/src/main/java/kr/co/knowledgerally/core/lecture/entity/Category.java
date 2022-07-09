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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "category_name")
    private Name name;

    @Builder.Default
    @Column(nullable = false)
    private boolean isActive = true;

    public enum Name {
        PM,
        DESIGN,
        DEVELOP,
        MARKETING,
        LANGUAGE,
        ETC
    }
}
