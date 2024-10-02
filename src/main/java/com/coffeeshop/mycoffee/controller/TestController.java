package com.coffeeshop.mycoffee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("hello")
    public String greeting(){
        return "Hello world! This is a response for set up successfully";
    }
}
