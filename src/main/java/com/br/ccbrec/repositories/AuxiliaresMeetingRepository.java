package com.br.ccbrec.repositories;

import com.br.ccbrec.entities.AuxiliaresMeeting;
import com.br.ccbrec.entities.YouthCult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuxiliaresMeetingRepository extends JpaRepository<AuxiliaresMeeting, Long> {
    @Query(value = "SELECT am.* FROM auxiliares_meeting am INNER JOIN youth_cult yc ON am.cult_id = yc.cult_id " +
            "WHERE yc.year= ?1 ORDER BY yc.cult_id desc LIMIT ?2", nativeQuery = true)
    public List<AuxiliaresMeeting> findByYear(String year, int limit);

    public AuxiliaresMeeting findByYouthCult(YouthCult yc);
}
