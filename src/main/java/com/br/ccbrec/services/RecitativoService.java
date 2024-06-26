package com.br.ccbrec.services;

import com.br.ccbrec.dto.DTO;
import com.br.ccbrec.dto.RecitativosDTO;
import com.br.ccbrec.dto.YouthCultDTO;
import com.br.ccbrec.entities.Recitativos;
import com.br.ccbrec.entities.YouthCult;
import com.br.ccbrec.enums.RecitativosSide;
import com.br.ccbrec.repositories.RecitativosRepository;
import com.br.ccbrec.util.DateUtils;
import com.br.ccbrec.util.SplitedDate;
import jakarta.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecitativoService implements IService {
    @Autowired
    private RecitativosRepository repository;

    @Autowired
    private YouthCultService cultService;

    public List<RecitativosDTO> getRecitativosByMonth() {
        Calendar today = Calendar.getInstance();

        String firstMonth = DateUtils.normalizeDayOrMonth(String.valueOf(today.get(Calendar.MONTH) + 1));
        String firstYear = DateUtils.normalizeDayOrMonth(String.valueOf(today.get(Calendar.YEAR)));
        int firstDay = today.get(Calendar.DAY_OF_MONTH);

        List<Recitativos> entities = this.repository.findByYearAndMonthAndStartDay(firstYear, firstMonth, firstDay);
        String nextMonth = DateUtils.normalizeDayOrMonth(String.valueOf(Integer.parseInt(firstMonth) + 1));
        entities.addAll(this.repository.findByYearAndMonth(firstYear, nextMonth));

        Collections.sort(entities, new Comparator<Recitativos>() {
            @Override
            public int compare(Recitativos o1, Recitativos o2) {
                YouthCult yc = o1.getYouthCult();
                YouthCult yc2 = o2.getYouthCult();
                SplitedDate sp = new SplitedDate(yc.getDay(), yc.getMonth(), yc.getYear());
                SplitedDate sp2 = new SplitedDate(yc2.getDay(), yc2.getMonth(), yc2.getYear());

                if (!sp.equals(sp2)) {
                    return DateUtils.compareSplitedDate(sp, sp2);
                }

                if (o1.getSex() != o2.getSex()) {
                    if (o1.getSex() == 'M') return -1;
                    if (o2.getSex() == 'M') return 1;
                }

                return Integer.compare(o1.getOrder(), o2.getOrder());
            }
        });

        return entities.stream().map((e) -> (RecitativosDTO) RecitativosDTO.fromEntity(e)).collect(Collectors.toList());
    }

    public List<RecitativosDTO> getRecitativosByDateAndFilters(String year, String month, int order, RecitativosSide side) {
        if (year == null) {
            year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        }

        if (month == null) {
            month = DateUtils.normalizeDayOrMonth(String.valueOf(Calendar.getInstance().get(Calendar.MONTH)));
        }

        List<Recitativos> recitativos = this.repository.findByYearAndMonthAndOrderAndSex(year, month, order, side.getValue());

        List<RecitativosDTO> response = recitativos.stream().map((recitativo) -> {
            return (RecitativosDTO) RecitativosDTO.fromEntity((Entity) recitativo);
        }).collect(Collectors.toList());

        Collections.sort(response, new Comparator<RecitativosDTO>() {
            @Override
            public int compare(RecitativosDTO o1, RecitativosDTO o2) {
                return DateUtils.compareDate(o1.getDate(), o2.getDate());
            }
        });

        return response;
    }

    @Override
    public DTO add(DTO dto) {
        RecitativosDTO recDto = (RecitativosDTO) dto;
        SplitedDate sp = DateUtils.splitRecitativosDate(recDto.getDate());

        Recitativos rec = this.repository.findByYearAndMonthAndDayAndOrderAndSex(sp.getYear(), sp.getMonth(), sp.getDay(),
                recDto.getOrder(), recDto.getSide().getValue());

        if (rec != null) {
            this.update(dto);
            return recDto;
        }

        YouthCult cult = this.cultService.cultExists(sp.getYear(), sp.getMonth(), sp.getDay());

        if (cult == null) {
            YouthCultDTO newDTO = new YouthCultDTO(sp.getYear(), sp.getMonth(), sp.getDay());
            this.cultService.add(newDTO);
            cult = this.cultService.cultExists(sp.getYear(), sp.getMonth(), sp.getDay());
        }

        rec = new Recitativos();
        rec.setBook(recDto.getBook());
        rec.setChapter(recDto.getChapter());
        rec.setOrder(recDto.getOrder());
        rec.setSex(recDto.getSide().getValue());
        rec.setFirstVerse(recDto.getFirstVerse());
        rec.setYouthCult(cult);
        this.repository.save(rec);

        return recDto;
    }

    @Override
    public void delete(SplitedDate dt) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public void update(DTO dto) {
        RecitativosDTO recDto = (RecitativosDTO) dto;
        SplitedDate date = DateUtils.splitRecitativosDate(recDto.getDate());

        Recitativos rec = this.repository.findByYearAndMonthAndDayAndOrderAndSex(date.getYear(), date.getMonth(),
                date.getDay(), recDto.getOrder(), recDto.getSide().getValue());

        rec.setBook(recDto.getBook());
        rec.setChapter(recDto.getChapter());
        rec.setOrder(recDto.getOrder());
        rec.setFirstVerse(recDto.getFirstVerse());

        this.repository.save(rec);
    }
}
