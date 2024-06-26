package com.br.ccbrec.repositories;

import com.br.ccbrec.entities.Recitativos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecitativosRepository extends JpaRepository<Recitativos, Integer> {

    @Query(value = "SELECT r.* FROM recitativos r INNER JOIN youth_cult yc ON " +
            "r.cult_id = yc.cult_id WHERE yc.year = ?1 AND yc.month = ?2 " +
            "AND r.recitativos_order = ?3 AND r.sex = ?4", nativeQuery = true)
    public List<Recitativos> findByYearAndMonthAndOrderAndSex(String year, String month, int order, char side);

    @Query(value = "SELECT r.* FROM recitativos r INNER JOIN youth_cult yc ON " +
            "r.cult_id = yc.cult_id WHERE yc.year = ?1 AND yc.month = ?2 ", nativeQuery = true)
    public List<Recitativos> findByYearAndMonth(String year, String month);

    @Query(value = "SELECT r.* FROM recitativos r INNER JOIN youth_cult yc ON " +
            "r.cult_id = yc.cult_id WHERE yc.year = ?1 AND yc.month = ?2 AND cast(yc.day AS INTEGER) >= ?3",
            nativeQuery = true)
    public List<Recitativos> findByYearAndMonthAndStartDay(String year, String month, int startDay);

    @Query(value = "SELECT r.* FROM recitativos r INNER JOIN youth_cult yc ON " +
            "r.cult_id = yc.cult_id WHERE yc.year = ?1 AND yc.month = ?2 AND yc.day = ?3 " +
            "AND r.recitativos_order = ?4 AND r.sex = ?5", nativeQuery = true)
    public Recitativos findByYearAndMonthAndDayAndOrderAndSex(String year, String month, String day, int order, char side);
}
