package com.coffeeshop.mycoffee.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
@Tag(name = "Test", description = "APIs for testing")
public class TestController {
    @GetMapping("/hello")
    public String greeting(){
        return "Hello world! This is a response for set up successfully";
    }
}
