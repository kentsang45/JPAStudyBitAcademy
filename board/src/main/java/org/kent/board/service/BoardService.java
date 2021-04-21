package org.kent.board.service;


import org.kent.board.dto.BoardDTO;
import org.kent.board.dto.PageRequestDTO;
import org.kent.board.dto.PageResultDTO;
import org.kent.board.entity.Board;
import org.kent.board.entity.Member;
import org.springframework.data.domain.Pageable;

public interface BoardService {

    Long register(BoardDTO dto);

    PageResultDTO<BoardDTO, Object[]> getPagedList(PageRequestDTO pageRquestDTO);

    BoardDTO get(Long bno);

    void removeWithReplies(Long bno);

    void modify(BoardDTO dto);

    default Board dtoToEntity(BoardDTO dto){

        Member writer = Member.builder()
                .email(dto.getWriterEmail()).build();

        Board board = Board.builder()
                .title(dto.getTitle())
                .writer(writer)
                .content(dto.getContent())
                .build();

        return board;
    }

    default BoardDTO entityToDto(Board board, Member member, Long replyCount){
        return BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())

                .writerEmail(member.getEmail())
                .writerName(member.getName())

                .replyCount(replyCount.intValue())
                .build();
    }

}
