package com.br.ccbrec.dto;

import com.br.ccbrec.entities.YouthCult;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YouthCultDTO extends DTO{
    private String year;
    private String month;
    private String day;

    public static YouthCultDTO fromEntity(Entity entity){
        YouthCult youthCult = (YouthCult) entity;
        return new YouthCultDTO(youthCult.getYear(), youthCult.getMonth(), youthCult.getDay());
    }

    public Entity toEntity(){
        return new YouthCult(this.year, this.month, this.day);
    }
}
