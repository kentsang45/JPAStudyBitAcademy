package org.kent.board.entity;

import lombok.*;

import javax.persistence.*;

@ToString(exclude = "writer")
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;
    private String title;
    private String content;

    // writer는 없다... 연관관계로 추가할 예정이다.
    // FK 로 설계
    @ManyToOne (fetch = FetchType.LAZY)
    private Member writer;
}

