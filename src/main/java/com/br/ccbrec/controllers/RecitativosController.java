package com.br.ccbrec.controllers;

import com.br.ccbrec.dto.ProfileDTO;
import com.br.ccbrec.dto.RecitativosDTO;
import com.br.ccbrec.enums.RecitativosSide;
import com.br.ccbrec.services.AuthService;
import com.br.ccbrec.services.ProfileService;
import com.br.ccbrec.services.RecitativoService;
import com.br.ccbrec.util.DateUtils;
import com.br.ccbrec.util.DataParameterWrapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/web/recitativos")
@AllArgsConstructor
public class RecitativosController {
    private AuthService authService;
    private RecitativoService service;
    private ProfileService profileService;

    @GetMapping
    public String recitativos(Model model, String year, String month, int order, String side) {
        ProfileDTO profileDTO = profileService.getProfileLogged(SecurityContextHolder.getContext());
        String mostPrivilegedUserRole = this.authService.getMostPrivileges(SecurityContextHolder.getContext()
                .getAuthentication().getName()).toString();
        RecitativosSide sideEnum = side.equalsIgnoreCase("MAN") ? RecitativosSide.MAN : RecitativosSide.WOMEN;
        List<RecitativosDTO> dtos = this.service.getRecitativosByDateAndFilters(year, month, order, sideEnum);

        model.addAttribute("mostPrivilege", mostPrivilegedUserRole);
        model.addAttribute("username", profileDTO.getUsername());
        model.addAttribute("profilePhotoUrl", profileDTO.getImage());
        model.addAttribute("isFormVisible", "none");
        model.addAttribute("dtos", dtos);
        model.addAttribute("recitativosDTO", new RecitativosDTO());
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("order", order);
        model.addAttribute("side", side);

        return "recitativos/index";
    }

    @PostMapping("/add")
    public String addRecitativos(Model model, @Valid RecitativosDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "recitativos/index";
        }

        RecitativosDTO recDto = (RecitativosDTO) this.service.add(dto);
        DataParameterWrapper sp = DateUtils.splitRecitativosDate(recDto.getDate());

        return String.format("redirect:/web/recitativos?year=%s&month=%s&order=%s&side=%s", sp.getYear(),
                sp.getMonth(), recDto.getOrder(), recDto.getSide());
    }

    @PutMapping("/update")
    public String updateRecitativos(Model model, @Valid RecitativosDTO recDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "recitativos/index";
        }

        DataParameterWrapper sp = DateUtils.splitRecitativosDate(recDto.getDate());
        this.service.update(recDto);

        return String.format("redirect:/web/recitativos?year=%s&month=%s&order=%s&side=%s", sp.getYear(),
                sp.getMonth(), recDto.getOrder(), recDto.getSide());
    }

    @GetMapping("/delete")
    public String deleteRecitativos(Model model, String date) {
        DataParameterWrapper sp = DateUtils.splitRecitativosDate(date);
        this.service.delete(sp);

        return String.format("redirect:/web/recitativos?year=%s&month=%s&order=%s&side=%s", sp.getYear(),
                sp.getMonth(), 1, "MAN");
    }

}
