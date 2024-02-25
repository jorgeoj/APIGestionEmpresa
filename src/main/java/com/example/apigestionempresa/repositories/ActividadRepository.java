package com.example.apigestionempresa.repositories;


import com.example.apigestionempresa.model.Actividad;
import com.example.apigestionempresa.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {
    /**
     * Obtiene todas las actividades de un alumno
     *
     * @param alumno El objeto Alumno que representa al alumno
     * @return La lista de actividades asociadas al alumno concreto
     */
    @Query("SELECT a FROM Actividad a where a.alumno=:alumno")
    public List<Actividad> getAllByAlumno(@Param("alumno") Alumno alumno);
}
