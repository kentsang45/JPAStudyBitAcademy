package org.kent.board.repository;

import org.kent.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // 1.
    @Query("select b from Board b where b.bno = :bno")
    Board getBoard(Long bno);

    // 2.Board는 Reply는 모르지만 Reply는 안다.
    // 2개 이상의 다른 Entity를 가져오는 경우 Object를 반환한다.
//    @Query("select b, r from Board b left join Reply r on r.board = b where b.bno = :bno")
//    Object[] getBoardWithReplyCount(Long bno);

    // 3. 특정한 게시물에 댓글이 몇개 있는지, 그 해당 게시물 가져오기
    @Query("select b, count(r) from Board b left join Reply r on r.board = b where b.bno = :bno " +
            "group by b")
    Object[] getBoardWithReplyCount(Long bno);

    // 4. 위를 페이징 처리...
    @Query("select b, count(r) from Board b left join Reply r on r.board = b " +
            "group by b")
    Page<Object[]> getBoardPageWithReplyCount(Pageable page);

    // 5. 게시글, 작성자, 댓글 수 모두 가져오기
//    @Query("select b, w, count(r) from Board b inner join b.writer w " +
//            "left join Reply r on r.board = b " +
//            "group by b ")
//    Page<Object[]> getBoardPageWithReplyCountAndMember(Pageable page);

    // 6. 카운트 성능을 높여주기 위해서 countQuery를 사용한다!!!
     @Query(value = "select b, w, count(r) from Board b " +
             "inner join b.writer w " +
             "left join Reply r On r.board = b " +
             "group by b", countQuery = "select count(b) from Board b")
     Page<Object[]> getBoardPageWithReplyCountAndMember(Pageable page);

     @Query(value = "select b, w, count(r) from Board b " +
             "inner join b.writer w " +
             "left join Reply r on r.board = b " +
             "where b.bno = :bno group by b")
     Object getBoardByBno(Long bno);
}
