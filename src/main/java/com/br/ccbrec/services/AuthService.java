package com.br.ccbrec.services;

import com.br.ccbrec.dto.DTO;
import com.br.ccbrec.dto.UserChangeRoleDTO;
import com.br.ccbrec.dto.UserDTO;
import com.br.ccbrec.entities.Roles;
import com.br.ccbrec.entities.User;
import com.br.ccbrec.enums.ChangeUserOperation;
import com.br.ccbrec.enums.RoleName;
import com.br.ccbrec.repositories.RolesRepository;
import com.br.ccbrec.repositories.UsersRepository;
import com.br.ccbrec.security.WebSecurityConfig;
import com.br.ccbrec.util.DataParameterWrapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class AuthService implements IService {
    private final UserDetailsImpl userDetailsImpl;
    private UsersRepository usersRepository;
    private RolesRepository rolesRepository;
    private WebSecurityConfig securityConfig;
    private DataSource dataSource;

    public RoleName getMostPrivileges(String username) {
        List<GrantedAuthority> authorityList = (List<GrantedAuthority>) this.usersRepository.findByUsername(username).getAuthorities();
        RoleName mostPrivileged = RoleName.ROLE_READER;

        for (GrantedAuthority authority : authorityList) {
            RoleName rn = ((Roles) authority).getRoleName();

            if (rn == RoleName.ROLE_ADMIN) {
                return RoleName.ROLE_ADMIN;
            }

            if (mostPrivileged.equals(RoleName.ROLE_READER) && rn.equals(RoleName.ROLE_AUXILIAR)) {
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

    public List<UserDTO> getAllUsersRegistered() {
        List<User> users = this.usersRepository.findAll();
        return users.stream().map(x -> UserDTO.fromEntity(x, this.getMostPrivileges(x.getUsername())))
                .collect(Collectors.toList());
    }

    public Boolean changeRoleForUser(UserChangeRoleDTO dto) {
        User user = this.usersRepository.findByUsername(dto.getUsername());
        RoleName rn = this.getNextRoleName(dto.getRole());
        RoleName newRole;

        if (dto.getOperation().equals(ChangeUserOperation.UP)) {
            newRole = this.getNextRoleName(rn);
            return this.updateUserRole(user, newRole);
        }

        if (dto.getOperation().equals(ChangeUserOperation.DOWN)) {
            newRole = this.getPreviousRoleName(rn);
            return this.updateUserRole(user, newRole);
        }

        if (dto.getOperation().equals(ChangeUserOperation.DELETE)) {
            this.usersRepository.delete(user);
        }

        return false;
    }

    private Boolean updateUserRole(User user, RoleName rn) {
        if (user == null) {
            return false;
        }

        if (user.getRoles().contains(rn)) {
            return false;
        }

        Roles r = this.rolesRepository.findByRoleName(rn);

        if (r != null) {
            User userClone = new User(user.getId(), user.getUsername(), user.getPassword(), List.of(r));
            this.usersRepository.save(userClone);
            return true;
        }

        return false;
    }

    private RoleName getNextRoleName(RoleName role) {
        RoleName rn;

        switch (role) {
            case ROLE_AUXILIAR, ROLE_ADMIN -> rn = RoleName.ROLE_ADMIN;
            case ROLE_READER -> rn = RoleName.ROLE_AUXILIAR;
            default -> rn = RoleName.ROLE_READER;
        }

        return rn;
    }

    private RoleName getPreviousRoleName(RoleName role) {
        RoleName rn;

        switch (role) {
            case ROLE_ADMIN -> rn = RoleName.ROLE_AUXILIAR;
            default -> rn = RoleName.ROLE_READER;
        }

        return rn;
    }


    @Override
    public DTO add(DTO dto) {
        var encoder = securityConfig.passwordEncoder();
        Long id = this.getLastUserId() + 1;

        UserDTO newDto = (UserDTO) dto;
        Roles roles = rolesRepository.findByRoleName(newDto.getMostPrivilege());
        User user = new User();
        user.setId(id);
        user.setUsername(newDto.getUsername());
        user.setPassword(encoder.encode(newDto.getPassword()));
        user.setRoles(List.of(roles));

        this.usersRepository.save(user);
        return dto;
    }

    @Override
    public void delete(DataParameterWrapper dto) {

    }

    @Override
    public void update(DTO dto) {

    }

    private long getLastUserId() {
        return this.usersRepository.count();
    }
}
