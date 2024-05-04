package com.br.ccbrec.services;

import com.br.ccbrec.dto.RecitativosCountDTO;
import com.br.ccbrec.entities.RecitativosCount;
import com.br.ccbrec.repositories.RecitativosCountRepository;
import com.br.ccbrec.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for query counts from database ans send it to views
 */
@Service
public class RecitativosCountService {
    @Autowired
    private RecitativosCountRepository repository;

    public List<RecitativosCountDTO> getCountsByDate(String month, String year) {
        month = DateUtils.normalizeDayOrMonth(month);

        List<RecitativosCount> countList = this.repository.findByYearAndMonth(year.trim(), month.trim());
        List<RecitativosCountDTO> output = new ArrayList<>();

        countList.forEach(
                (count) -> {
                    output.add(RecitativosCountDTO.fromDTO(count));
                }
        );

        return output;
    }

    public RecitativosCount addNewCount(RecitativosCountDTO count) throws Exception {
        Map<String, String> splitedDate = DateUtils.splitRecitativosDate(count.getDate());
        boolean dateExists = this.dateExists(splitedDate.get("day"), splitedDate.get("month"),
                splitedDate.get("year"));

        if(dateExists){
            throw new Exception("date already exists");
        }

        // checking if day selected is sunday, idk how to do this
        //TODO: See how  to implement weekday validation

        RecitativosCount entityCount = count.toEntity();
        this.repository.save(entityCount);

        return entityCount;
    }

    private boolean dateExists(String day, String month, String year) {
        month = DateUtils.normalizeDayOrMonth(month);
        day = DateUtils.normalizeDayOrMonth(day);

        RecitativosCount count = this.repository.findByYearAndMonthAndDay(year, month, day);
        return count != null;
    }
}
