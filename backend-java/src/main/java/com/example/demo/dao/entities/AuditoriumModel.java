package com.example.demo.dao.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "auditorium")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class AuditoriumModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "block", nullable = false)
    private String block;

    @Column(name = "ident")
    private String ident;
}
