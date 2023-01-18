package com.example.WebMvcTest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

//@NoArgsConstructor
//@Getter
//public class Book {
//    private Integer idx;
//    private String title; //제목
//    private LocalDateTime publishedAt; //출간일자
//
//    @Builder
//    public Book(String title, LocalDateTime publishedAt) {
//        this.title = title;
//        this.publishedAt = publishedAt;
//    }
//}

@NoArgsConstructor
@Getter
@Entity
@Table
public class Book {

    @Id
    @GeneratedValue
    private Integer idx;

    @Column
    private String title; //제목

    @Column
    private LocalDateTime publishedAt; //출간일자

    @Builder
    public Book(String title, LocalDateTime publishedAt) {
        this.title = title;
        this.publishedAt = publishedAt;
    }
}
