package com.br.ccbrec.repositories;

import com.br.ccbrec.entities.RecitativosCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecitativosCountRepository extends JpaRepository<RecitativosCount, Long> {
    public List<RecitativosCount> findByYearAndMonth(String year, String month);

    public RecitativosCount findByYearAndMonthAndDay(String year, String month, String day);
}
