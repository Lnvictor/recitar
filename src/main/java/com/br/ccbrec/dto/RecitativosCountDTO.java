package com.br.ccbrec.dto;

import com.br.ccbrec.entities.RecitativosCount;
import com.br.ccbrec.entities.YouthCult;
import com.br.ccbrec.util.DateUtils;
import com.br.ccbrec.util.SplitedDate;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecitativosCountDTO extends DTO{
    @NotBlank
    private String date;

    @NotNull
    @PositiveOrZero
    private int girls;

    @NotNull
    @PositiveOrZero
    private int boys;

    @NotNull
    @PositiveOrZero
    private int youngGirls;

    @NotNull
    @PositiveOrZero
    private int youngBoys;

    @NotNull
    @PositiveOrZero
    private int individuals;

    public static RecitativosCountDTO fromEntity(RecitativosCount count) {
        return new RecitativosCountDTO(RecitativosCountDTO.getFormattedDate(count.getYouthCult().getDay(),
                count.getYouthCult().getMonth(), count.getYouthCult().getYear()), count.getGirls(), count.getBoys(),
                count.getYoungGirls(), count.getYoungBoys(), count.getIndividuals());
    }

    public static String getFormattedDate(String day, String month, String year) {
        return String.format("%s/%s/%s", day, month, year);
    }

    public int getTotal() {
        return this.boys + this.youngBoys + this.girls + this.youngGirls + this.individuals;
    }

    public RecitativosCount toEntity() {
        RecitativosCount entity = new RecitativosCount();
        SplitedDate map = DateUtils.splitRecitativosDate(this.date);

        YouthCult youthCult = new YouthCult();
        youthCult.setYear(map.getYear());
        youthCult.setMonth(map.getMonth());
        youthCult.setDay(map.getDay());

        entity.setYouthCult(youthCult);
        entity.setBoys(this.boys);
        entity.setGirls(this.girls);
        entity.setYoungBoys(this.youngBoys);
        entity.setYoungGirls(this.youngGirls);
        entity.setIndividuals(this.individuals);

        return entity;
    }
}
