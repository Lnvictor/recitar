package com.br.ccbrec.dto;

import com.br.ccbrec.entities.User;
import com.br.ccbrec.enums.RoleName;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends DTO {
    private String username;
    private String password;
    private RoleName mostPrivilege;

    public static UserDTO fromEntity(User user){
        return new UserDTO(user.getUsername(), user.getPassword(), null);
    }

    public static UserDTO fromEntity(User user, RoleName roleName){
        return new UserDTO(user.getUsername(), user.getPassword(), roleName);
    }
}
