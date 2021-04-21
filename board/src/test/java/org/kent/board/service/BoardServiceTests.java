package org.kent.board.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.kent.board.dto.BoardDTO;
import org.kent.board.dto.PageRequestDTO;
import org.kent.board.dto.PageResultDTO;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

@Log4j2
@SpringBootTest
public class BoardServiceTests {

    @Autowired
    BoardService service;

    @Test
    public void testRegister(){

        BoardDTO dto = BoardDTO.builder()
                .title("테스트 타이틀2")
                .content("테스트 컨텐츠2")
                .writerEmail("USER_10@aaa.com")
                .build();

        Long bno = service.register(dto);

        log.info(bno);

    }

    @Test
    public void testPagedList(){

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO result = service.getPagedList(pageRequestDTO);

        result.getDtoList().forEach(d->log.info(d));

    }

    @Test
    public void testGet(){
        BoardDTO dto = service.get(100L);

        log.info(dto);
    }

    @Test
    public void testDelete(){
        service.removeWithReplies(97L);
    }

    @Test
    public void testModify(){
        BoardDTO dto = BoardDTO.builder()
                .bno(101L)
                .title("수정된_제목_101").content("수정된_내용_101")
                .build();

        service.modify(dto);

        log.info(service.get(101L));
    }
}
