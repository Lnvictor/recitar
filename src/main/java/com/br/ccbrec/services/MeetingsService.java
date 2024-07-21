package com.br.ccbrec.services;

import com.br.ccbrec.dto.AuxiliaresMeetingDTO;
import com.br.ccbrec.dto.DTO;
import com.br.ccbrec.entities.AuxiliaresMeeting;
import com.br.ccbrec.entities.YouthCult;
import com.br.ccbrec.repositories.AuxiliaresMeetingRepository;
import com.br.ccbrec.repositories.YouthCultRepository;
import com.br.ccbrec.util.DateUtils;
import com.br.ccbrec.util.DataParameterWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MeetingsService implements IService {
    private AuxiliaresMeetingRepository repository;
    private YouthCultRepository cultRepository;

    public List<AuxiliaresMeetingDTO> getRealizedMeetings(String year) {
        List<AuxiliaresMeeting> entitites = this.repository.findByYear(year, 10);

        List<AuxiliaresMeetingDTO> dtos = entitites.stream().map(
                (entity) -> {
                    return AuxiliaresMeetingDTO.fromEntity(entity);
                }
        ).collect(Collectors.toList());

        Collections.sort(dtos, (o1, o2) -> DateUtils.compareDate(o1.getDate(), o2.getDate()));

        return dtos;
    }

    public DTO add(DTO dto) {
        AuxiliaresMeetingDTO auxiliaresMeetingDTO = (AuxiliaresMeetingDTO) dto;
        DataParameterWrapper spDate = DateUtils.splitRecitativosDate(auxiliaresMeetingDTO.getDate());
        YouthCult cult = this.cultRepository.findByYearAndMonthAndDay(spDate.getYear(), spDate.getMonth(), spDate.getDay());

        if (cult == null) {
            cult = new YouthCult();
            cult.setDay(spDate.getDay());
            cult.setMonth(spDate.getMonth());
            cult.setYear(spDate.getYear());
            this.cultRepository.save(cult);
        }

        AuxiliaresMeeting meeting = new AuxiliaresMeeting();
        meeting.setYouthCult(cult);
        meeting.setNotes(auxiliaresMeetingDTO.getNotes());
        this.repository.save(meeting);

        return auxiliaresMeetingDTO;
    }

    public AuxiliaresMeetingDTO getDetailsFromDate(String date) {
        DataParameterWrapper sp = DateUtils.splitRecitativosDate(DateUtils.transformBrIntoPattern(date));
        YouthCult cult = this.cultRepository.findByYearAndMonthAndDay(sp.getYear(), sp.getMonth(), sp.getDay());

        if (cult == null) {
            throw new RuntimeException("Youth cult is not registered in database");
        }

        AuxiliaresMeeting meeting = this.repository.findByYouthCult(cult);

        if (meeting == null) {
            throw new RuntimeException("Meeting does not exists, how can I get detail of a thing who doesnt exists???????");
        }

        return AuxiliaresMeetingDTO.fromEntity(meeting);

    }

    /*
     * Not Implemented YET
     * */

    @Override
    public void delete(DataParameterWrapper dto) {
        return;
    }

    @Override
    public void update(DTO dto) {
        return;
    }
}
