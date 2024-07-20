package com.br.ccbrec.dto;

import com.br.ccbrec.entities.Recitativos;
import com.br.ccbrec.enums.RecitativosSide;
import com.br.ccbrec.util.DateUtils;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecitativosDTO extends DTO {
    @Positive
    private int order;
    private RecitativosSide side;

    @NotBlank
    private String date;

    @NotBlank
    private String book;

    @Positive
    private int chapter;

    @Positive
    private int firstVerse;

    public static Object fromEntity(Entity entity){
        Recitativos recitativos = (Recitativos) entity;
        RecitativosSide side = recitativos.getSex() == 'H' ? RecitativosSide.MAN : RecitativosSide.WOMEN;
        String date = DateUtils.transformWrapperDateIntoStr(recitativos.getYouthCult().getYear(),
                recitativos.getYouthCult().getMonth(), recitativos.getYouthCult().getDay());

        return new RecitativosDTO(recitativos.getOrder(), side, date, recitativos.getBook(), recitativos.getChapter(),
                recitativos.getFirstVerse());
    }

    public String getPtBrSide(){
        if (this.side == null){
            return null;
        }

        if (this.side == RecitativosSide.MAN){
            return this.order <= 2 ? "Meninos" : "Moços";
        }
        else{
            return this.order <= 2 ? "Meninas" : "Moças";
        }
    }
}
