package com.example.apigestionempresa.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Clase que representa una actividad realizada por un alumno.
 */
@Entity
@Table(name = "actividad")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idactividad;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;
    private String observacion;
    private Integer horas;
    private String tipo;
    private String actividad;

    @ManyToOne
    @JoinColumn(name = "alumno", referencedColumnName = "idalumno")
    private Alumno alumno;

    /**
     * Constructor vacío de la clase Actividad.
     */
    public Actividad() {}

    // Métodos getter y setter para los atributos de la clase

    /**
     * Obtiene el ID de la actividad.
     * @return El ID de la actividad.
     */
    public Long getIdactividad() {
        return idactividad;
    }

    /**
     * Establece el ID de la actividad.
     * @param idactividad El ID de la actividad.
     */
    public void setIdactividad(Long idactividad) {
        this.idactividad = idactividad;
    }

    /**
     * Obtiene la fecha de la actividad.
     * @return La fecha de la actividad.
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha de la actividad.
     * @param fecha La fecha de la actividad.
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene las observaciones de la actividad.
     * @return Las observaciones de la actividad.
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * Establece las observaciones de la actividad.
     * @param observacion Las observaciones de la actividad.
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    /**
     * Obtiene las horas dedicadas a la actividad.
     * @return Las horas dedicadas a la actividad.
     */
    public Integer getHoras() {
        return horas;
    }

    /**
     * Establece las horas dedicadas a la actividad.
     * @param horas Las horas dedicadas a la actividad.
     */
    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    /**
     * Obtiene el tipo de actividad.
     * @return El tipo de actividad.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de actividad.
     * @param tipo El tipo de actividad.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la descripción de la actividad.
     * @return La descripción de la actividad.
     */
    public String getActividad() {
        return actividad;
    }

    /**
     * Establece la descripción de la actividad.
     * @param actividad La descripción de la actividad.
     */
    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    /**
     * Obtiene el alumno asociado a la actividad.
     * @return El alumno asociado a la actividad.
     */
    public Alumno getAlumno() {
        return alumno;
    }

    /**
     * Establece el alumno asociado a la actividad.
     * @param alumno El alumno asociado a la actividad.
     */
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
}
