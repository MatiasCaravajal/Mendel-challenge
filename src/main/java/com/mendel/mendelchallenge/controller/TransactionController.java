package com.mendel.mendelchallenge.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

public class TransactionController {

    @RestController
    public class HelloWorld {

        @GetMapping("/helloWorld")
        public String Hello() {
            return "Hello world";
        }
    }
}
