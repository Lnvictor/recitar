package com.br.ccbrec.controllers;

import com.br.ccbrec.dto.UserChangeRoleDTO;
import com.br.ccbrec.dto.UserDTO;
import com.br.ccbrec.services.AuthService;
import com.br.ccbrec.services.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "web/settings")
@AllArgsConstructor
public class RolesManagementController {
    private AuthService authService;
    private ProfileService profileService;

    @GetMapping
    public String settingsIndex(Model model) {
        var context = SecurityContextHolder.getContext();
        List<UserDTO> users = this.authService.getAllUsersRegistered();

        model.addAttribute("mostPrivilege", authService.getMostPrivileges(context.getAuthentication().getName()));
        model.addAttribute("profilePhotoUrl", profileService.getProfileLogged(context).getImage());
        model.addAttribute("users", users);

        return "settings/index";
    }

    @PostMapping("/change")
    public String changeRole(UserChangeRoleDTO dto) {
        this.authService.changeRoleForUser(dto);
        return "redirect:/web/settings";
    }

    @PostMapping("/addUser")
    public String addUser(UserDTO dto) {
        this.authService.add(dto);

        return "redirect:/web/settings";
    }
}
