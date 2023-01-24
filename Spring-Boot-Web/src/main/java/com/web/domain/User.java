package com.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    private LocalDateTime createdDate; //생성 날짜

    @Column
    private LocalDateTime updatedDate; //수정 날짜

    @Builder
    public User(String name, String password, String email, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

}
