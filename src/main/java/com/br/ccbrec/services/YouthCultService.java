package com.br.ccbrec.services;

import com.br.ccbrec.dto.DTO;
import com.br.ccbrec.dto.YouthCultDTO;
import com.br.ccbrec.entities.YouthCult;
import com.br.ccbrec.repositories.YouthCultRepository;
import com.br.ccbrec.util.SplitedDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YouthCultService implements IService{

    @Autowired
    private YouthCultRepository cultRepository;

    public YouthCult cultExists(String year, String month, String day) {
        if (year == null || month == null || day == null) return null;


        return this.cultRepository.findByYearAndMonthAndDay(year, month, day);
    }

    @Override
    public DTO add(DTO dto) {
        YouthCultDTO realDTO = (YouthCultDTO) dto;
        this.cultRepository.save((YouthCult) realDTO.toEntity());

        return realDTO;
    }

    @Override
    public void delete(SplitedDate splitedDate) {
        YouthCult yc = this.cultRepository.findByYearAndMonthAndDay(splitedDate.getYear(), splitedDate.getMonth(),
                splitedDate.getDay());

        this.cultRepository.delete(yc);
    }


    @Override
    public void update(DTO dto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
