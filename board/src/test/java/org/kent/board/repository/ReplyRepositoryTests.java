package org.kent.board.repository;


import org.junit.jupiter.api.Test;
import org.kent.board.entity.Board;
import org.kent.board.entity.Reply;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;



    @Test
    public void testInsertReply(){
        // 게시글의 번호가 필요하다.
        IntStream.rangeClosed(1,300).forEach(i->{
            int index = (int)(Math.random() * 100) + 1;
            Board board = Board.builder()
                    .bno((long)index)
                    .build();

            Reply reply = Reply.builder()
                    .replyer("replyer_" + (i % 10))
                    .text("댓글 내용 ... " + index)
                    .board(board)
                    .build();

            replyRepository.save(reply);
        });

    }

}
