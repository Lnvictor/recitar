package com.br.ccbrec.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.Annotation;

@Data
@NoArgsConstructor
@Entity
@Table(name = "youth_cult")
public class YouthCult implements Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cultId;

    @Column(name = "day", nullable = false)
    private String day;

    @Column(name = "month", nullable = false)
    private String month;

    @Column(name = "year", nullable = false)
    private String year;

    public YouthCult(String year, String month, String day) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @Override
    public String name() {
        return "";
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
