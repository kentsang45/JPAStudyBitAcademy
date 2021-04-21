package org.kent.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.kent.board.dto.BoardDTO;
import org.kent.board.dto.PageRequestDTO;
import org.kent.board.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        log.info("============ BOARD LIST ============");

        model.addAttribute("result", boardService.getPagedList(pageRequestDTO));

    }

    @GetMapping("/register")
    public void getRegister(){

        log.info("============ GET REGISTER ============");

    }

    @PostMapping("/register")
    public String postRegister(BoardDTO dto, RedirectAttributes redirectAttributes){

        log.info("============ POST REGISTER ============");
        log.info("DTO : " +dto);

        Long bno = boardService.register(dto);

        redirectAttributes.addFlashAttribute("bno", bno);

        return "redirect:/board/list";

    }
}
