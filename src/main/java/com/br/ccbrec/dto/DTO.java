package com.br.ccbrec.dto;

import jakarta.persistence.Entity;
import jdk.jshell.spi.ExecutionControl;

public abstract class DTO {
    public static Object fromEntity(Entity entity) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("It doesnt have implementation in interface");
    }

    public Object toEntity() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("It doesnt have implementation in interface");
    }
}
