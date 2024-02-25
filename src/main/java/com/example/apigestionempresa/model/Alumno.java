package com.example.apigestionpracticasempresa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "alumno")
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idalumno;
    private String nombre;
    private String apellidos;
    private String contrasenya;
    private String dni;
    private Date nacimiento;
    private String email;
    private Integer telefono;
    @ManyToOne
    @JoinColumn(name = "empresa", referencedColumnName = "idempresa")
    private Empresa empresa;
    @ManyToOne
    @JoinColumn(name = "tutor", referencedColumnName = "idprofesor")
    private Profesor tutor;
    private Integer horasdual;
    private Integer horasfct;
    private String observaciones;
    @JsonIgnore
    @OneToMany(mappedBy = "alumno", fetch = FetchType.EAGER)
    private List<Actividad> actividad_diaria = new ArrayList<>();
}
