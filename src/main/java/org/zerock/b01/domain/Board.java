package org.zerock.b01.domain;

import lombok.*;

import javax.persistence.*;

@Entity // 테이블을 만들어줌. 클래스명 or (name="element") 로 지정가능
@Builder // 이 빌드를 만들수있게함
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board extends BaseEntity{

    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 pk // 오라클은 sequence , mysql은 identity
    private Long bno;

    @Column(length = 500, nullable = false) // varchar(500) not null 이란 뜻
    private String title;

    @Column(length = 2000, nullable = false) // varchar(2000) not null 이란 뜻
    private String content;

    @Column(length = 50, nullable = false, updatable = false) // 업데이트를 허용하지 않겠다.
    private String writer;

    public void change(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
