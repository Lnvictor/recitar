package com.br.ccbrec.controllers;

import com.br.ccbrec.dto.DTO;
import com.br.ccbrec.dto.ProfileDTO;
import com.br.ccbrec.services.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@AllArgsConstructor
public class ProfileController {
    private ProfileService service;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getProfilePage(Model model) {
        SecurityContext context = SecurityContextHolder.getContext();
        ProfileDTO dto = service.getProfileLogged(context);

        model.addAttribute("image", dto.getImage());
        model.addAttribute("username", dto.getUsername());
        model.addAttribute("role", dto.getRoleName());
        return "profiles/index";
    }

    @PostMapping(value = "/profile/add")
    public String UpdateProfile(Model model, @RequestParam("image") MultipartFile file) {
        DTO dto = new ProfileDTO(SecurityContextHolder.getContext().getAuthentication().getName(), file);
        service.add(dto);

        model.addAttribute("dto", dto);
        return "redirect:/profile";
    }
}
