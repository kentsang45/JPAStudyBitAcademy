package org.kent.demo.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@ToString // 이것을 조심해서 써야한다...
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Memo {

    // pk는 null이 들어갈 수 있도록 타입을 잡아야한다.
    @Id
    // auto increment, 어떤 방식으로? =strategy
    // identity가 autoincrement이다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    // 그냥 만들면 기본 사이즈 (255자)
    // 수정하려면 ...
    @Column(length = 200, nullable = false)
    private String memoText;

    // setter는 아니지만 setter인...
    // 이것이 필요하다.
    public void changeText(String text){
        this.memoText = text;
    }
}
