package com.abit.mockservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/response")
@Slf4j
public class RestStatusCheckController {

    @GetMapping("/200")
    public ResponseEntity<String> get200Status() {
        log.info("get200Status");
        return ResponseEntity.ok("200");
    }

    @GetMapping("/500")
    public ResponseEntity<String> get500Status() {
        log.info("get500Status");
        return ResponseEntity.internalServerError().build();
    }
}