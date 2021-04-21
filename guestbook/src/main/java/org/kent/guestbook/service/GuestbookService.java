package org.kent.guestbook.service;


import org.kent.guestbook.dto.GuestbookDTO;
import org.kent.guestbook.dto.PageRequestDTO;
import org.kent.guestbook.dto.PageResultDTO;
import org.kent.guestbook.entity.Guestbook;

public interface GuestbookService {

    // 등록
    Long register(GuestbookDTO dto);

    // PageRequest => PageResult 바꿔주는 함수
    PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);

    default Guestbook dtoToEntity(GuestbookDTO dto){

        return Guestbook.builder()
                .gno(dto.getGno()).writer(dto.getWriter()).content(dto.getContent()).title(dto.getTitle())
                .build();

    }

    default GuestbookDTO entityToDto(Guestbook entity){

        return GuestbookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

    }
}
