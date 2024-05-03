package com.br.ccbrec.controllers;

import com.br.ccbrec.dto.RecitativosCountDTO;
import com.br.ccbrec.services.RecitativosCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/web/ccbrec")
public class ContagemController {
    @Autowired
    private RecitativosCountService service;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model, @RequestParam String year, @RequestParam String month) {
        List<RecitativosCountDTO> dtos = service.getCountsByDate(month, year);
        model.addAttribute("counts", dtos);
        return "index";
    }
}
