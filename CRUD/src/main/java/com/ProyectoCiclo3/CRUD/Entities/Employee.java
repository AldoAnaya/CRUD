package com.ProyectoCiclo3.CRUD.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "Nombres")
    private String name;

    @Column(name="Emails",unique=true)
    private String email;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "employee")
    private Profile profile;

    @Enumerated(EnumType.STRING)
    private Enum_role role;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Enterprise.class)
    @JoinColumn(name = "id_enterprise")
    private Enterprise enterprise;

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    private LocalDate updateAt;
    private LocalDate createdAt;
}
