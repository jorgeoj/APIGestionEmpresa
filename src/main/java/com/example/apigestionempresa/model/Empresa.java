package com.example.apigestionempresa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "empresas")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idempresa;
    private Integer telefono;
    private String email;
    private String responsable;
    private String observaciones;
    private String nombre;
    @JsonIgnore
    @OneToMany(mappedBy = "empresa",fetch = FetchType.EAGER)
    private List<Alumno> alumnos = new ArrayList<>();
}
