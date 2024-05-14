package com.br.ccbrec.services;

import com.br.ccbrec.entities.Roles;
import com.br.ccbrec.enums.RoleName;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    public RoleName getMostPrivileges(SecurityContext context){
        List<GrantedAuthority> authorityList = (List<GrantedAuthority>) context.getAuthentication().getAuthorities();

        for (GrantedAuthority authority : authorityList) {
            RoleName rn = ((Roles) authority).getRoleName();

            if (rn == RoleName.ROLE_ADMIN) {
                return RoleName.ROLE_ADMIN;
            }
        }

        return RoleName.ROLE_READER;
    }
}
