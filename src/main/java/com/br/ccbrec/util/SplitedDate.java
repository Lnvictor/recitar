package com.br.ccbrec.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SplitedDate {
    private String day;
    private String month;
    private String year;

    public String convertIntoBRDateStr(){
        return DateUtils.transformSplitedDateIntoStr(this.year, this.month, this.day);
    }

    @Override
    public boolean equals(Object object){
        if (!(object instanceof SplitedDate)){
            throw new IllegalArgumentException("Parametro deve ser instancia de SplitedDate");
        }

        SplitedDate other = (SplitedDate) object;

        return this.year.equals(other.getYear()) && this.month.equals(other.getMonth()) &&
                this.day.equals(other.getDay());
    }
}
