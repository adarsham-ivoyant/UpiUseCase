package com.ivoyant.upiusecase;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckDeployment {
    @GetMapping("/deploycheck")
    public String checkDeployment() {
        return "Hello World";
    }
}
