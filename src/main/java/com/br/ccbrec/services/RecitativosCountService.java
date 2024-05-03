package com.br.ccbrec.services;

import com.br.ccbrec.dto.RecitativosCountDTO;
import com.br.ccbrec.entities.RecitativosCount;
import com.br.ccbrec.repositories.RecitativosCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class is responsible for query counts from database ans send it to views
 */
@Service
public class RecitativosCountService {
    @Autowired
    private RecitativosCountRepository repository;

    public List<RecitativosCountDTO> getCountsByDate(String month, String year) {
        List<RecitativosCount> countList = this.repository.findByYearAndMonth(year.trim(), month.trim());
        List<RecitativosCountDTO> output = new ArrayList<>();

        countList.forEach(
                (count) -> {
                    output.add(RecitativosCountDTO.fromDTO(count));
                }
        );

        return output;
    }
}
