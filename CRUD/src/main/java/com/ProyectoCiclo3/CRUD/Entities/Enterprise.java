package com.ProyectoCiclo3.CRUD.Entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Enterprise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String document;
    private String phone;
    private String address;
    @OneToMany(mappedBy = "enterprise",cascade = CascadeType.ALL)
    private List<Employee> employees;
    @OneToMany(mappedBy = "enterprise",cascade = CascadeType.ALL)
    private List<Transaction> transactions;
    private LocalDate createdAt;
    private LocalDate updatedAt;

}
