package org.kent.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@Builder
@AllArgsConstructor
@Log4j2
public class PageRequestDTO {


    private int page;
    private int size;
    private String type;
    private String keyword;

    public PageRequestDTO(){
        page = 1;
        size = 10;
        log.info(page + " , " + size);
    }

    public Pageable getPageable(Sort sort){
        return PageRequest.of(page - 1, size, sort);
    }

}
