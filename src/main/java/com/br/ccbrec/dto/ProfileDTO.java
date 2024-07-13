package com.br.ccbrec.dto;

import com.br.ccbrec.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileDTO extends DTO {
    private String username;
    private String roleName;
    private Object image;

    public ProfileDTO(String username, Object image){
        super();
        this.username = username;
        this.image = image;
    }
}
