package com.br.ccbrec.services;

import com.br.ccbrec.dto.DTO;
import com.br.ccbrec.dto.UserDTO;
import com.br.ccbrec.entities.Roles;
import com.br.ccbrec.entities.User;
import com.br.ccbrec.enums.RoleName;
import com.br.ccbrec.repositories.RolesRepository;
import com.br.ccbrec.repositories.UsersRepository;
import com.br.ccbrec.util.DataParameterWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService implements IService{

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

    public RoleName getMostPrivileges(SecurityContext context) {
        List<GrantedAuthority> authorityList = (List<GrantedAuthority>) context.getAuthentication().getAuthorities();
        RoleName mostPrivileged = RoleName.ROLE_READER;

        for (GrantedAuthority authority : authorityList) {
            RoleName rn = ((Roles) authority).getRoleName();

            if (rn == RoleName.ROLE_ADMIN) {
                return RoleName.ROLE_ADMIN;
            }

            if (mostPrivileged.equals(RoleName.ROLE_READER) && rn.equals(RoleName.ROLE_AUXILIAR)){
                mostPrivileged = RoleName.ROLE_AUXILIAR;
            }
        }

        return mostPrivileged;
    }

    public List<UserDTO> getActiveUsersByRole(RoleName roleName) {
        Roles roles = this.rolesRepository.findByRoleName(roleName);
        List<User> users = this.usersRepository.findByRoles(roles);
        List<UserDTO> dtos = new ArrayList<>();

        for (User user : users) {
            dtos.add(UserDTO.fromEntity(user));
        }

        return dtos;
    }


    @Override
    public DTO add(DTO dto) {
        return null;
    }

    @Override
    public void delete(DataParameterWrapper dto) {

    }

    @Override
    public void update(DTO dto) {

    }
}
