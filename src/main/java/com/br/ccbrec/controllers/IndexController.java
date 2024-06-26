package com.br.ccbrec.controllers;

import com.br.ccbrec.util.DateUtils;
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
        String month = DateUtils.normalizeDayOrMonth(String.valueOf(now.get(Calendar.MONTH) + 1));

        return String.format("redirect:/web/ccbrec?year=%s&month=%s", year, month);
    }
}
