package com.br.ccbrec.controllers;

import com.br.ccbrec.dto.ProfileDTO;
import com.br.ccbrec.dto.RecitativosCountDTO;
import com.br.ccbrec.enums.RoleName;
import com.br.ccbrec.services.AuthService;
import com.br.ccbrec.services.ProfileService;
import com.br.ccbrec.services.RecitativosCountService;
import com.br.ccbrec.util.DateUtils;
import com.br.ccbrec.util.SplitedDate;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/web/ccbrec")
public class ContagemController {
    @Autowired
    private RecitativosCountService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private ProfileService profileService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model, @RequestParam String year, @RequestParam String month) {
        List<RecitativosCountDTO> dtos = service.getCountsByDate(month, year);
        RoleName mostPrivilege = this.authService.getMostPrivileges(SecurityContextHolder.getContext());
        ProfileDTO profileDTO = profileService.getProfileLogged(SecurityContextHolder.getContext());

        model.addAttribute("isFormVisible", "none");
        model.addAttribute("username", profileDTO.getUsername());
        model.addAttribute("counts", dtos);
        model.addAttribute("recitativosCountDTO", new RecitativosCountDTO());
        model.addAttribute("mostPrivilege", mostPrivilege.toString());
        model.addAttribute("month", month);
        model.addAttribute("year", year);
        model.addAttribute("profilePhotoUrl", profileDTO.getImage());

        return "ccbrec/index";
    }

    @PostMapping("/add")
    public String addNewCount(@Valid RecitativosCountDTO formDTO, BindingResult bindingResult, Model model) {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("isFormVisible", "default");
                return "ccbrec/index";
            }

            RecitativosCountDTO entityCount = (RecitativosCountDTO) this.service.add(formDTO);
            SplitedDate sp = DateUtils.splitRecitativosDate(formDTO.getDate());
            return String.format("redirect:/web/ccbrec?year=%s&month=%s", sp.getYear(), sp.getMonth());
        } catch (Exception exception) {
            return "error";
        }
    }

    @GetMapping("/delete")
    public String removeCount(@RequestParam String date) {
        SplitedDate splitedDate = DateUtils.splitRecitativosDate(DateUtils.transformBrIntoPattern(date));
        this.service.delete(splitedDate);
        return String.format("redirect:/web/ccbrec?year=%s&month=%s", splitedDate.getYear(), splitedDate.getMonth());
    }
}
