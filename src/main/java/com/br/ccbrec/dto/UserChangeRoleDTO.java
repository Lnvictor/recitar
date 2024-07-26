package com.br.ccbrec.dto;

import com.br.ccbrec.enums.ChangeUserOperation;
import com.br.ccbrec.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserChangeRoleDTO extends DTO {
    private String username;
    private RoleName role;
    private ChangeUserOperation operation;
}
