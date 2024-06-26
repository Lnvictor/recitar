package com.br.ccbrec.controllers;

import com.br.ccbrec.dto.AuxiliaresMeetingDTO;
import com.br.ccbrec.dto.DTO;
import com.br.ccbrec.dto.RecitativosDTO;
import com.br.ccbrec.dto.UserDTO;
import com.br.ccbrec.enums.RoleName;
import com.br.ccbrec.services.AuthService;
import com.br.ccbrec.services.MeetingsService;
import com.br.ccbrec.services.RecitativoService;
import com.br.ccbrec.util.DateUtils;
import com.br.ccbrec.util.SplitedDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/web/meetings")
public class MeetingsController {

    @Autowired
    private RecitativoService recitativoService;

    @Autowired
    private MeetingsService meetingsService;

    @Autowired
    private AuthService authService;

    @GetMapping
    public String meetingsIndex(Model model, String year) {
        if (year == null) {
            year = "2024";
        }

        List<AuxiliaresMeetingDTO> dtos = this.meetingsService.getRealizedMeetings(year);
        String mostPrivilegedUserRole = this.authService.getMostPrivileges(SecurityContextHolder.getContext()).toString();

        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("dates", dtos);
        model.addAttribute("mostPrivilege", mostPrivilegedUserRole);
        model.addAttribute("year", year);

        return "meetings/index";
    }

    @GetMapping("/add")
    public String newMeeting(Model model) {
        List<UserDTO> auxiliares = this.authService.getActiveUsersByRole(RoleName.ROLE_AUXILIAR);
        List<RecitativosDTO> nextRecitativos = this.recitativoService.getRecitativosByMonth();

        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("auxiliares", auxiliares);
        model.addAttribute("nextRecitativos", nextRecitativos);
        model.addAttribute("auxiliaresMeetingDTO", new AuxiliaresMeetingDTO());

        return "meetings/new_meeting";
    }

    @PostMapping("/add")
    public String addMeeting(Model model, AuxiliaresMeetingDTO auxiliaresMeetingDTO) {
        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        this.meetingsService.add(auxiliaresMeetingDTO);

        return "redirect:/web/meetings";
    }

    @GetMapping("/detail")
    public String getMeetingDetail(@RequestParam String date, Model model) {
        AuxiliaresMeetingDTO dto = this.meetingsService.getDetailsFromDate(date);

        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("dto", dto);
        model.addAttribute("date", DateUtils.transformBrIntoPattern(date));
        return "meetings/detail";
    }
}
