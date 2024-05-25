package com.br.ccbrec.repositories;

import com.br.ccbrec.entities.RecitativosCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecitativosCountRepository extends JpaRepository<RecitativosCount, Long> {
    @Query(value = "SELECT rc.* FROM recitativos_count rc INNER JOIN youth_cult yc ON yc.cult_id = rc.cult_id" +
            " WHERE yc.year = ?1 AND yc.month = ?2", nativeQuery = true)
    public List<RecitativosCount> findByYearAndMonth(String year, String month);

    @Query(value = "SELECT rc.* FROM recitativos_count rc INNER JOIN youth_cult yc ON yc.cult_id = rc.cult_id" +
            " WHERE yc.year = ?1 AND yc.month = ?2 AND yc.day = ?3", nativeQuery = true)
    public RecitativosCount findByYearAndMonthAndDay(String year, String month, String day);
}
