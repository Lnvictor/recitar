package com.br.ccbrec.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller()
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String sayHello() {
        return "hello";
    }
}
