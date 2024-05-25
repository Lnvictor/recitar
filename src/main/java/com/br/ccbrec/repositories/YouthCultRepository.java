package com.br.ccbrec.repositories;

import com.br.ccbrec.entities.YouthCult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface YouthCultRepository extends JpaRepository<YouthCult, Long> {
    public YouthCult findByYearAndMonthAndDay(String year, String month, String day);

    public List<YouthCult> findTop1ByOrderByCultIdDesc();
}
