package com.br.ccbrec.dto;

import com.br.ccbrec.entities.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String username;
    private String password;

    public static UserDTO fromEntity(User user){
        return new UserDTO(user.getUsername(), user.getPassword());
    }
}
