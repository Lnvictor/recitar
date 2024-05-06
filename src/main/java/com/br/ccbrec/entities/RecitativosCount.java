package com.br.ccbrec.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "recitativos_count")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RecitativosCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year")
    private String year;

    @Column(name = "month")
    private String month;

    @Column(name = "day")
    private String day;

    @Column(name = "girls")
    private int girls;

    @Column(name = "boys")
    private  int boys;

    @Column(name = "young_girls")
    private int youngGirls;

    @Column(name = "young_boys")
    private int youngBoys;

    @Column(name = "individuals")
    private int individuals;
}
