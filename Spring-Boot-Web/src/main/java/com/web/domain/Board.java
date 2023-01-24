package com.web.domain;

import com.web.domain.enums.BoardType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;


import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name="BOARD_TABLE")
public class Board implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; //PK: BoardTable의 인덱스

    @Column
    private String title; //제목

    @Column
    private String subTitle; //서브 타이틀

    @Column
    private String content;//내용

    @Column
    @Enumerated(EnumType.STRING)
    private BoardType boardType; //게시판 타입

    @Column
    private LocalDateTime createdDate; //생성 날짜

    @Column
    private LocalDateTime updatedDate; //수정 날짜

    @OneToOne(fetch = FetchType.LAZY)
    private User user; //회원

    @Builder
    public Board(String title, String subTitle, String content, BoardType boardType, LocalDateTime createdDate, LocalDateTime updatedDate, User user) {
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
        this.boardType = boardType;
        this.createdDate = createdDate;
        this.updatedDate =  updatedDate;
        this.user = user;
    }
}
