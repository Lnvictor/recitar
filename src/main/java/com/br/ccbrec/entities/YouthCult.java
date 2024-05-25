package com.br.ccbrec.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name = "youth_cult")
public class YouthCult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cultId;

    @Column(name = "day", nullable = false)
    private String day;

    @Column(name = "month", nullable = false)
    private String month;

    @Column(name = "year", nullable = false)
    private String year;
}
