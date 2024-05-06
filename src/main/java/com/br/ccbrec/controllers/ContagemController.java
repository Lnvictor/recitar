package com.br.ccbrec.controllers;

import com.br.ccbrec.dto.RecitativosCountDTO;
import com.br.ccbrec.entities.RecitativosCount;
import com.br.ccbrec.services.RecitativosCountService;
import com.br.ccbrec.util.DateUtils;
import com.br.ccbrec.util.SplitedDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/web/ccbrec")
public class ContagemController {
    @Autowired
    private RecitativosCountService service;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model, @RequestParam String year, @RequestParam String month) {
        List<RecitativosCountDTO> dtos = service.getCountsByDate(month, year);

        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("counts", dtos);
        model.addAttribute("formDTO", new RecitativosCountDTO());
        return "index";
    }

    @PostMapping("/addNewCount")
    public String addNewCount(@ModelAttribute RecitativosCountDTO formDTO) {
        try {
            RecitativosCount entityCount = this.service.addNewCount(formDTO);
            return String.format("redirect:/web/ccbrec?year=%s&month=%s", entityCount.getYear(), entityCount.getMonth());
        } catch (Exception exception) {
            return "error";
        }
    }

    @GetMapping("/removeCount")
    public String removeCount(@RequestParam String date) {
        SplitedDate splitedDate = DateUtils.splitRecitativosDate(DateUtils.transformBrIntoPattern(date));
        this.service.deleteCount(splitedDate);
        return String.format("redirect:/web/ccbrec?year=%s&month=%s", splitedDate.getYear(), splitedDate.getMonth());
    }
}
