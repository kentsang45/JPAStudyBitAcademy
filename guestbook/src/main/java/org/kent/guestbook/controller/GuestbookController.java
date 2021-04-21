package org.kent.guestbook.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.kent.guestbook.dto.GuestbookDTO;
import org.kent.guestbook.dto.PageRequestDTO;
import org.kent.guestbook.dto.PageResultDTO;
import org.kent.guestbook.entity.Guestbook;
import org.kent.guestbook.service.GuestbookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/guestbook")
@Log4j2
public class GuestbookController {

    private final GuestbookService service;

    @GetMapping({"/list", "/"})
    public String getList(PageRequestDTO pageRequest, Model model){

        log.info("========== GET LIST ==========");
        log.info(pageRequest);

        PageResultDTO<GuestbookDTO, Guestbook> resultList = service.getList(pageRequest);
        // 목록 데이터 담기
        log.info(resultList);
        model.addAttribute("result", resultList);
        model.addAttribute("pageRequestDTO", pageRequest);
        return "/guestbook/list";
    }

    @GetMapping("/register")
    public void register(){
        log.info("REGISTER");
    }


}
