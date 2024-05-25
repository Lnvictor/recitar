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
}
