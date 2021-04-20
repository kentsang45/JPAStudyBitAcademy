package org.kent.d3.controller;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.kent.d3.dto.SampleDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Log4j2
@RequestMapping("/sample")
public class SampleController {

    @GetMapping("/ex1")
    public void ex1(){
        log.info("EX1");
    }

    @GetMapping("/ex2")
    public void ex2(Model model){
        log.info("EX2");
        // asLongStream... Long을 넣으면 Object(SampleDTO가 나온다.
        List<SampleDTO> list = IntStream.rangeClosed(1, 100).asLongStream()
                .mapToObj(i->{
                    return SampleDTO.builder()
                            .sno(i).first("FIRST_" + i).last("LAST_" + i).regTime(LocalDateTime.now())
                            .build();
                }).collect(Collectors.toList());

        model.addAttribute("list", list);
    }

    @GetMapping("/testLayout")
    public void testLayout(){
        log.info("TEST LAYOUT");
    }
}
