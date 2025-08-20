package com.dockeep.demo.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DummyController {
    @GetMapping("/private")
    public String secured(){
        return "Private endpoint";
    }

    @GetMapping("/public")
    public String unsecured(){
        return "Public endpoint";
    }
}
