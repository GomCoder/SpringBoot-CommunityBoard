package com.web;

import com.web.domain.Board;
import com.web.domain.User;
import com.web.domain.enums.BoardType;
import com.web.repository.BoardRepository;
import com.web.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaMappingTest {
    private final String boardTestTitle = "테스트";
    private final String email = "test@gmail.com";

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Before
    public void init() {
        //User 데이터 저장
        User user = userRepository.save(
                User.builder()
                        .name("devk")
                        .password("test")
                        .email(email)
                        .createdDate(LocalDateTime.now())
                        .build()
        );

        //Board 데이터 저장
        boardRepository.save(
                Board.builder()
                        .title(boardTestTitle)
                        .subTitle("서브 타이블")
                        .content("콘텐츠")
                        .boardType(BoardType.free)
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .user(user)
                        .build()
        );
    }

    //테스트하기
    @Test
    public void Create_Date_Test() {
        //User 생성
        User user = userRepository.findByEmail(email);
        assertThat(user.getName(), is("devk"));
        assertThat(user.getPassword(), is("test"));
        assertThat(user.getEmail(), is(email));

        //게시판글 생성
        Board board = boardRepository.findByUser(user);
        assertThat(board.getTitle(), is(boardTestTitle));
        assertThat(board.getSubTitle(), is("서브 타이블"));
        assertThat(board.getContent(), is("콘텐츠"));
        assertThat(board.getBoardType(), is(BoardType.free));
    }
}
