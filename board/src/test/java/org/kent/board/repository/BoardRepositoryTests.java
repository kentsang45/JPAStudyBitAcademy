package org.kent.board.repository;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.kent.board.entity.Board;
import org.kent.board.entity.Member;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsertBoards(){

        IntStream.rangeClosed(1,100).forEach(b->{
            // 우선 회원을 만들어야한다. FK...
            int randomIndex = (int)(Math.random() * 100) + 1; // 0~99.99999 -> 0~99 -> 1~100
            // "USER_" +m+"@aaa.com"
            Member member = Member.builder()
                    .email("USER_" +randomIndex+"@aaa.com")
                    .build();

            Board board = Board.builder()
                .title("TITLE_" + b)
                .content("CONTENT_" + b)
                .writer(member) // Member 추가
                .build();

            boardRepository.save(board);
        });

    }

    @Test
    public void testRead(){

        Optional<Board> result = boardRepository.findById(100L);

        log.info(result);

        if(result.isPresent()){
            Board board = result.get();
            log.info(board);
//            Member member = board.getWriter();
//            log.info(member);
        }

    }

    @Test
    public void testList(){
        // 이렇게 하면 Member를 모두 조회해서 쿼리가 10번 넘게 날아간다.
        // 이것은 ToString 때문이다... ToString에서 Member를 필요로하기 때문에 Member를 조회해온다.
        // list조회 시 자동 join이 바람직하지 않다...
        // Eager... <-> Lazy
        // Eager... 한번에 조인해서 가져오는 Loading방식
        // Member가 필요하지 않으면 Member가 필요할 때 까지 미룬다.
        // DB 성능상 Lazy가 바람직하다.
        // 기본으로 EagerLoading방식을 사용한다. 따라서 바꿔줘야한다. =>    @ManyToOne (fetch = FetchType.LAZY)
        // 그런데 FindById를 통해 실행하면 오류가 난다.
        // No Session 에러... => 쿼리를 한번밖에 못보내는 상황이라서 LazyLoading이 필요할 때는 쿼리를 한번 더 보내야한다.
        // 이때 Transactional이 필요하다. 그러나 결국 쿼리 2번 날리기... 등등의 문제가 발생
        // 또는 @ToString(exclude = "writer")를 통해 Member를 String화 하지 않는다!
        // 결국 @Query로 처리 가능하다. => 수동으로 Join하는 함수를 하나 만든다!
        Pageable page = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.findAll(page);

        result.getContent().forEach(b->log.info(b));
    }

}
