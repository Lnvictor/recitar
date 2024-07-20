package com.br.ccbrec.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataParameterWrapper {
    private String day;
    private String month;
    private String year;

    public String convertIntoBRDateStr(){
        return DateUtils.transformWrapperDateIntoStr(this.year, this.month, this.day);
    }

    @Override
    public boolean equals(Object object){
        if (!(object instanceof DataParameterWrapper)){
            throw new IllegalArgumentException("Parametro deve ser instancia de SplitedDate");
        }

        DataParameterWrapper other = (DataParameterWrapper) object;

        return this.year.equals(other.getYear()) && this.month.equals(other.getMonth()) &&
                this.day.equals(other.getDay());
    }
}
