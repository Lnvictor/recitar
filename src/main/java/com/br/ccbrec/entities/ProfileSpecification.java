package com.br.ccbrec.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Calendar;

@Data
@Entity
@Table(name = "profile_specification")
public class ProfileSpecification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "VARCHAR(500)")
    private String profilePhotoUrl;

    private Calendar photoUrlGeneratedAt;

    private String profilePhotoImageName;

    private Integer photoExpiresIn;
}
