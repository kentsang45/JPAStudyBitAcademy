package org.kent.board.service;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.kent.board.dto.BoardDTO;
import org.kent.board.dto.PageRequestDTO;
import org.kent.board.dto.PageResultDTO;
import org.kent.board.entity.Board;
import org.kent.board.entity.Member;
import org.kent.board.repository.BoardRepository;
import org.kent.board.repository.ReplyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Log4j2
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDTO dto) {
        Board board = dtoToEntity(dto);
        boardRepository.save(board);

        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getPagedList(PageRequestDTO pageRquestDTO) {

        Pageable page = pageRquestDTO.getPageable(Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getBoardPageWithReplyCountAndMember(page);

        Function<Object[], BoardDTO> function = (arr -> entityToDto(
                (Board)arr[0], (Member)arr[1], (Long)arr[2]
        ));

        return new PageResultDTO<>(result, function);
    }

    @Override
    public BoardDTO get(Long bno) {
        Object[] arr = (Object[])boardRepository.getBoardByBno(bno);

        return entityToDto((Board)arr[0], (Member)arr[1], (Long)arr[2]);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {
        replyRepository.deleteByBno(bno);
        boardRepository.deleteById(bno);
    }

    @Transactional
    @Override
    public void modify(BoardDTO dto) {
        // getOne을 하면 Transactional을 걸어야한다!!!!!!
        Board board = boardRepository.getOne(dto.getBno());

        board.changeContent(dto.getContent());
        board.changeTitle(dto.getTitle());

        boardRepository.save(board);
    }
}
