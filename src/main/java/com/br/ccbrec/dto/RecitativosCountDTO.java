package com.br.ccbrec.dto;

import com.br.ccbrec.entities.RecitativosCount;
import com.br.ccbrec.util.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecitativosCountDTO {
    private String date;

    private int girls;

    private int boys;

    private int youngGirls;

    private int youngBoys;

    private int individuals;

    public static RecitativosCountDTO fromDTO(RecitativosCount count) {
        return new RecitativosCountDTO(RecitativosCountDTO.getFormattedDate(count.getDay(), count.getMonth(), count.getYear()),
                count.getGirls(), count.getBoys(), count.getYoungGirls(), count.getYoungBoys(), count.getIndividuals());
    }

    public static String getFormattedDate(String day, String month, String year) {
        return String.format("%s/%s/%s", day, month, year);
    }

    public int getTotal() {
        return this.boys + this.youngBoys + this.girls + this.youngGirls + this.individuals;
    }

    public RecitativosCount toEntity() {
        RecitativosCount entity = new RecitativosCount();
        Map<String, String> map = DateUtils.splitRecitativosDate(this.date);

        entity.setYear(map.get("year"));
        entity.setMonth(map.get("month"));
        entity.setDay(map.get("day"));

        entity.setBoys(this.boys);
        entity.setGirls(this.girls);
        entity.setYoungBoys(this.youngBoys);
        entity.setYoungGirls(this.youngGirls);
        entity.setIndividuals(this.individuals);

        return entity;
    }
}
