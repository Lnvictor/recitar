package com.br.ccbrec.services;

import com.br.ccbrec.dto.DTO;
import com.br.ccbrec.dto.YouthCultDTO;
import com.br.ccbrec.entities.YouthCult;
import com.br.ccbrec.repositories.YouthCultRepository;
import com.br.ccbrec.util.DataParameterWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YouthCultService implements IService{

    @Autowired
    private YouthCultRepository cultRepository;

    public YouthCult cultExists(DataParameterWrapper dataParameterWrapper) {
        String day = dataParameterWrapper.getDay();
        String year = dataParameterWrapper.getYear();
        String month = dataParameterWrapper.getMonth();

        return this.cultRepository.findByYearAndMonthAndDay(year, month, day);
    }

    @Override
    public DTO add(DTO dto) {
        YouthCultDTO realDTO = (YouthCultDTO) dto;
        this.cultRepository.save((YouthCult) realDTO.toEntity());

        return realDTO;
    }

    @Override
    public void delete(DataParameterWrapper dataParameterWrapper) {
        YouthCult yc = this.cultRepository.findByYearAndMonthAndDay(dataParameterWrapper.getYear(), dataParameterWrapper.getMonth(),
                dataParameterWrapper.getDay());

        this.cultRepository.delete(yc);
    }


    @Override
    public void update(DTO dto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
