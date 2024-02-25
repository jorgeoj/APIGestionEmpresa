package com.example.apigestionempresa.repositories;

import com.example.apigestionempresa.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    /**
     * Verifica si existe un alumno por su email
     * @param email del alumno
     * @return Si el alumno con el email concreto existe o no
     */
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Alumno a WHERE a.email = ?1")
    public Boolean existsAlumnoByEmail(String email);
    /**
     * Obtiene al alumno por su email
     * @param email del alumno
     * @return El alumno que coincide con el email
     */

    @Query("SELECT a FROM Alumno a WHERE a.email = ?1")
    public Alumno getAlumnoByEmail(String email);



}
