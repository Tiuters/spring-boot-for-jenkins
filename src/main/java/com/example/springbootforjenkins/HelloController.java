package com.example.springbootforjenkins;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/jenkins")
    public String test() {
        return "<h1>Hello, Andrey??</h>";
    }
}
