package com.br.ccbrec.services;

import com.br.ccbrec.dto.AuxiliaresMeetingDTO;
import com.br.ccbrec.entities.AuxiliaresMeeting;
import com.br.ccbrec.entities.YouthCult;
import com.br.ccbrec.repositories.AuxiliaresMeetingRepository;
import com.br.ccbrec.repositories.YouthCultRepository;
import com.br.ccbrec.util.DateUtils;
import com.br.ccbrec.util.SplitedDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetingsService {
    @Autowired
    private AuxiliaresMeetingRepository repository;

    @Autowired
    private YouthCultRepository cultRepository;

    public List<SplitedDate> getCultsForNextMeeting() {
        List<YouthCult> latestCult = cultRepository.findTop1ByOrderByCultIdDesc();
        List<SplitedDate> nextCultDates = new ArrayList<>();

        // calculating dates for next two months TODO: DEFINE HOW MANY WEEKS UPCOMING CALCULATE
        for (int i = 1; i <= 8; i++) {
            int weekDaysToAdd = i * 7;

            nextCultDates.add(DateUtils.addDays(latestCult.getFirst().getYear(), latestCult.getFirst().getMonth(),
                    latestCult.getFirst().getDay(), weekDaysToAdd));
        }

        return nextCultDates;
    }

    public List<AuxiliaresMeetingDTO> getRealizedMeetings(String year) {
        List<AuxiliaresMeeting> entitites = this.repository.findByYear(year,10);

        List<AuxiliaresMeetingDTO> dtos = entitites.stream().map(
                (entity) -> {
                    return AuxiliaresMeetingDTO.fromEntity(entity);
                }
        ).collect(Collectors.toUnmodifiableList());

        return dtos;
    }

    public AuxiliaresMeetingDTO addMeeting(AuxiliaresMeetingDTO auxiliaresMeetingDTO) {
        SplitedDate spDate = DateUtils.splitRecitativosDate(auxiliaresMeetingDTO.getDate());
        YouthCult cult = this.cultRepository.findByYearAndMonthAndDay(spDate.getYear(), spDate.getMonth(),spDate.getDay());

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
        SplitedDate sp = DateUtils.splitRecitativosDate(DateUtils.transformBrIntoPattern(date));
        YouthCult cult = this.cultRepository.findByYearAndMonthAndDay(sp.getYear(), sp.getMonth(), sp.getDay());

        if (cult == null){
            throw new RuntimeException("Youth cult is not registered in database");
        }

        AuxiliaresMeeting meeting = this.repository.findByYouthCult(cult);

        if (meeting == null){
            throw new RuntimeException("Meeting does not exists, how can I get detail of a thing who doesnt exists???????");
        }

        return AuxiliaresMeetingDTO.fromEntity(meeting);

    }
}
