package com.br.ccbrec.enums;

import lombok.Getter;

@Getter
public enum RecitativosSide {
    WOMEN('M'),
    MAN('H');

    private char value;

    RecitativosSide(char value){
        this.value = value;
    }

    public static RecitativosSide fromString(String value){
        if (value.equals("Women")) return WOMEN;
        if (value.equals("Man")) return MAN;
        return null;
    }
}
