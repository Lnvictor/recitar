package com.br.ccbrec.services;

import com.br.ccbrec.dto.RecitativosCountDTO;
import com.br.ccbrec.entities.RecitativosCount;
import com.br.ccbrec.repositories.RecitativosCountRepository;
import com.br.ccbrec.util.DateUtils;
import com.br.ccbrec.util.SplitedDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

        Collections.sort(output, new Comparator<RecitativosCountDTO>() {
            @Override
            public int compare(RecitativosCountDTO dto1, RecitativosCountDTO dto2) {
                return dto1.getDate().compareTo(dto2.getDate());
            }
        });

        return output;
    }

    public RecitativosCount addNewCount(RecitativosCountDTO count) throws Exception {
        SplitedDate splitedDate = DateUtils.splitRecitativosDate(count.getDate());
        boolean dateExists = this.dateExists(splitedDate.getDay(), splitedDate.getMonth(), splitedDate.getYear());


        // idk if it is a good pratice, but its working! :)
        if (dateExists) {
            this.udpateCount(count);
            return count.toEntity();
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

    public void udpateCount(RecitativosCountDTO dto) throws Exception {
        SplitedDate splitedDate = DateUtils.splitRecitativosDate(dto.getDate());
        RecitativosCount count = this.repository.findByYearAndMonthAndDay(
                splitedDate.getYear(), splitedDate.getMonth(), splitedDate.getDay());

        if (count == null) {
            throw new Exception("User does not exists");
        }

        count.setBoys(dto.getBoys());
        count.setGirls(dto.getGirls());
        count.setYoungBoys(dto.getYoungBoys());
        count.setYoungGirls(dto.getYoungGirls());
        count.setIndividuals(dto.getIndividuals());

        this.repository.save(count);
        this.repository.save(count);
    }

    public void deleteCount(SplitedDate splitedDate) {
        RecitativosCount deletionCandidate = this.repository.findByYearAndMonthAndDay(
                splitedDate.getYear(), splitedDate.getMonth(), splitedDate.getDay());

        if (deletionCandidate != null) {
            this.repository.delete(deletionCandidate);
        }
    }
}
