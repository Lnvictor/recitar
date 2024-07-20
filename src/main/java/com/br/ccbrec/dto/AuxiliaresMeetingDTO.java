package com.br.ccbrec.dto;


import com.br.ccbrec.entities.AuxiliaresMeeting;
import com.br.ccbrec.util.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This DTO will be used to save meetings "ATA"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuxiliaresMeetingDTO extends DTO{
    private String date;
    private String notes;

    public static AuxiliaresMeetingDTO fromEntity(AuxiliaresMeeting entity) {
        String date = DateUtils.transformWrapperDateIntoStr(entity.getYouthCult().getYear(),
                entity.getYouthCult().getMonth(),
                entity.getYouthCult().getDay());

        return new AuxiliaresMeetingDTO(date, entity.getNotes());
    }

}
