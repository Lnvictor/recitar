package com.br.ccbrec.dto;

import com.br.ccbrec.entities.RecitativosCount;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecitativosCountDTO {
    private String year;

    private String month;

    private String day;

    private int girls;

    private int boys;

    private int youngGirls;

    private int youngBoys;

    private int individuals;

    public static RecitativosCountDTO fromDTO(RecitativosCount count) {
        return new RecitativosCountDTO(count.getYear(), count.getMonth(), count.getDay(),
                count.getGirls(), count.getBoys(), count.getYoungGirls(), count.getYoungBoys(), count.getIndividuals());
    }

    public String getFormattedDate() {
        return String.format("%s/%s/%s", this.getDay(), this.getMonth(), this.getYear());
    }

    public int getTotal() {
        return this.boys + this.youngBoys + this.girls + this.youngGirls + this.individuals;
    }
}
