package com.br.ccbrec.enums;

public enum RoleName {
    ROLE_ADMIN("Administrador do Sistema"),
    ROLE_READER("Acesso de Leitura"),
    ROLE_AUXILIAR("Auxiliar de Jovens");

    private final String value;

    RoleName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
