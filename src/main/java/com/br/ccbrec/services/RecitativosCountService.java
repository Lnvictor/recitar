package com.br.ccbrec.services;

import com.br.ccbrec.dto.DTO;
import com.br.ccbrec.dto.RecitativosCountDTO;
import com.br.ccbrec.entities.RecitativosCount;
import com.br.ccbrec.entities.YouthCult;
import com.br.ccbrec.repositories.RecitativosCountRepository;
import com.br.ccbrec.repositories.YouthCultRepository;
import com.br.ccbrec.util.DateUtils;
import com.br.ccbrec.util.SplitedDate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * This class is responsible for query counts from database ans send it to views
 */
@Transactional
@Service
public class RecitativosCountService implements IService {

    @Autowired
    private RecitativosCountRepository repository;

    @Autowired
    private YouthCultRepository cultRepository;

    @Autowired
    private YouthCultService cultService;

    public List<RecitativosCountDTO> getCountsByDate(String month, String year) {
        month = DateUtils.normalizeDayOrMonth(month);

        List<RecitativosCount> countList = this.repository.findByYearAndMonth(year.trim(), month.trim());
        List<RecitativosCountDTO> output = new ArrayList<>();

        countList.forEach(
                (count) -> {
                    output.add(RecitativosCountDTO.fromEntity(count));
                }
        );

        Collections.sort(output, (dto1, dto2) -> DateUtils.compareDate(dto1.getDate(), dto2.getDate()));

        return output;
    }

    public DTO add(DTO dto) {
        RecitativosCountDTO count = (RecitativosCountDTO) dto;
        SplitedDate splitedDate = DateUtils.splitRecitativosDate(count.getDate());
        boolean dateExists = this.dateExists(splitedDate.getDay(), splitedDate.getMonth(), splitedDate.getYear());

        if (dateExists) {
            this.update(count);
            return count;
        }

        // checking if day selected is sunday, idk how to do this
        //TODO: See how  to implement weekday validation

        RecitativosCount entityCount = count.toEntity();
        YouthCult cult = this.cultService.cultExists(splitedDate.getYear(), splitedDate.getMonth(), splitedDate.getDay());

        if (cult == null) {
            this.cultRepository.save(entityCount.getYouthCult());
        } else {
            entityCount.setYouthCult(cult);
        }

        this.repository.save(entityCount);

        return count;
    }

    private boolean dateExists(String day, String month, String year) {
        month = DateUtils.normalizeDayOrMonth(month);
        day = DateUtils.normalizeDayOrMonth(day);

        RecitativosCount count = this.repository.findByYearAndMonthAndDay(year, month, day);
        return count != null;
    }

    @Override
    public void update(DTO dto) {
        RecitativosCountDTO countDto = (RecitativosCountDTO) dto;

        SplitedDate splitedDate = DateUtils.splitRecitativosDate(countDto.getDate());
        RecitativosCount count = this.repository.findByYearAndMonthAndDay(
                splitedDate.getYear(), splitedDate.getMonth(), splitedDate.getDay());

        count.setBoys(countDto.getBoys());
        count.setGirls(countDto.getGirls());
        count.setYoungBoys(countDto.getYoungBoys());
        count.setYoungGirls(countDto.getYoungGirls());
        count.setIndividuals(countDto.getIndividuals());

        this.repository.save(count);
        this.repository.save(count);
    }

    @Override
    public void delete(SplitedDate splitedDate) {
        RecitativosCount deletionCandidate = this.repository.findByYearAndMonthAndDay(
                splitedDate.getYear(), splitedDate.getMonth(), splitedDate.getDay());

        if (deletionCandidate != null) {
            this.repository.delete(deletionCandidate);
        }
    }
}
