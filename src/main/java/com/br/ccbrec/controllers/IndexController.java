package com.br.ccbrec.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

@Controller
public class IndexController {
    @GetMapping
    public String index(){
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);

        return String.format("redirect:/web/ccbrec?year=%s&month=%s", year, month);
    }
}
