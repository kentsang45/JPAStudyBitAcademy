package org.kent.demo.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class SampleController {

    @GetMapping("/doA")
    public String[] doA(){
        log.info("TEST");
        return new String[]{"aaa", "bbb"};
    }

}
