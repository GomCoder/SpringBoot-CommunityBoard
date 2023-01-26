package com.web.domain;

import com.web.domain.enums.SocialType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name="USER_TABLE")
public class User implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;//PK: User 테이블 인덱스

    @Column
    private String name; //이름

    @Column
    private String password; //비밀번호

    @Column
    private String email; //이메일

    @Column
    private String principal; //OAuth2 인증으로 제공받는 키 값

    @Column
    @Enumerated(EnumType.STRING)
    private SocialType socialType; //어떤 소셜 미디어로 인증 받았는지 여부를 구분

    @Column
    private LocalDateTime createdDate; //생성 날짜

    @Column
    private LocalDateTime updatedDate; //수정 날짜

    @Builder
    public User(String name, String password, String email, String principal, SocialType socialType ,LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.principal = principal;
        this.socialType = socialType;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

}
