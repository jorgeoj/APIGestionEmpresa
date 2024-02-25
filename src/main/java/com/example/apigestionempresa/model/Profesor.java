package com.example.apigestionempresa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "profesor")
public class Profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idprofesor;
    private String nombre;
    private String apellidos;
    private String email;
    private String contrasenya;
    @JsonIgnore
    @OneToMany(mappedBy = "tutor", fetch = FetchType.EAGER)
    private List<Alumno> alumnos = new ArrayList<>();
}
