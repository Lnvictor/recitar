package com.br.ccbrec.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "auxiliares_meeting")
public class AuxiliaresMeeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Long meetingId;

    @OneToOne
    @JoinColumn(name = "cult_id")
    private YouthCult youthCult;

    @Column(name = "notes")
    private String notes;
}
