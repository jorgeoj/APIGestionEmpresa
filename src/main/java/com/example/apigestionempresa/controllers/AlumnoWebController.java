package com.example.apigestionempresa.controllers;

import com.example.apigestionempresa.model.Actividad;
import com.example.apigestionempresa.model.Alumno;
import com.example.apigestionempresa.repositories.ActividadRepository;
import com.example.apigestionempresa.repositories.AlumnoRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para manejar las operaciones relacionadas con los alumnos y sus actividades.
 */
@Controller
@RequestMapping("/")
public class AlumnoWebController {
    @Autowired
    private ActividadRepository actividadRepository;
    @Autowired
    private AlumnoRepository alumnoRepository;

    /**
     * Redirige a la página de inicio de sesión.
     * @return La ruta de redirección hacia la página de inicio de sesión.
     */
    @GetMapping("/")
    public String volverLogin(){
        return "redirect:/login";
    }

    /**
     * Obtiene las actividades para un alumno específico y las muestra en la vista principal.
     * @param idalumno ID del alumno.
     * @param model Modelo para agregar atributos a la vista.
     * @param request Objeto HttpServletRequest para acceder a la sesión.
     * @return La vista principal del alumno si está autenticado, de lo contrario, la vista de inicio de sesión.
     */
    @GetMapping("/{idalumno}")
    public String getActividadesByAlumno(@PathVariable Long idalumno, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Alumno alumnoSession = (Alumno) session.getAttribute("alumno");

        if(alumnoSession!=null){
            Alumno alumno = alumnoSession;
            List<Actividad> actividades = actividadRepository.getAllByAlumno(alumno);
            model.addAttribute("actividades",actividades);
            model.addAttribute("alumno", alumno);
            return "mainViewAlumno";
        }else{
            model.addAttribute("alumno",new Alumno());
            return "login";
        }
    }

    /**
     * Maneja el envío del formulario para añadir una nueva actividad.
     * @param idalumno ID del alumno.
     * @param fecha Fecha de la actividad.
     * @param horas Horas dedicadas a la actividad.
     * @param tipo Tipo de actividad.
     * @param actividad Descripción de la actividad.
     * @param observacion Observaciones sobre la actividad.
     * @param request Objeto HttpServletRequest para acceder a la sesión.
     * @return Redirección a la página del alumno después de añadir la actividad si está autenticado, de lo contrario, la vista de inicio de sesión.
     */
    @PostMapping("/new/{idalumno}")
    public String newActividad(@PathVariable Long idalumno, @RequestParam String fecha, @RequestParam Integer horas,
                               @RequestParam String tipo, @RequestParam String actividad,
                               @RequestParam String observacion, HttpServletRequest request) {

        // Obtiene la sesión actual del usuario
        HttpSession session = request.getSession();
        // Verifica si hay un alumno en sesión
        Alumno alumnoSession = (Alumno) session.getAttribute("alumno");

        if (alumnoSession != null) { // Si hay un alumno en sesión
            // Verifica que los datos de la actividad no estén vacíos
            if (fecha != null && !fecha.isEmpty() && tipo != null && !tipo.isEmpty() && actividad != null && !actividad.isEmpty() && observacion != null) {

                // Crea un formato de fecha para convertir la cadena de fecha en un objeto Date
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaDate;
                try {
                    // Convierte la cadena de fecha en un objeto Date
                    fechaDate = dateFormat.parse(fecha);
                } catch (ParseException e) {
                    // Maneja la excepción en caso de error al analizar la fecha
                    e.printStackTrace();
                    // Redirige de vuelta a la página de añadir actividad si hay un error en la fecha
                    return "redirect:/new/" + idalumno;
                }

                // Crea un nuevo objeto de actividad y establece sus atributos
                Actividad nuevaActividad = new Actividad();
                nuevaActividad.setFecha(fechaDate);
                nuevaActividad.setHoras(horas);
                nuevaActividad.setTipo(tipo);
                nuevaActividad.setActividad(actividad);
                nuevaActividad.setObservacion(observacion);
                nuevaActividad.setAlumno(alumnoSession);

                // Guarda la nueva actividad en la base de datos
                actividadRepository.save(nuevaActividad);

                // Redirige a la página del alumno después de guardar la actividad
                return "redirect:/" + idalumno;
            } else {
                // Redirige de vuelta a la página de añadir actividad si faltan datos
                return "redirect:/new/" + idalumno;
            }
        } else {
            // Si no hay un alumno en sesión, redirige a la página de inicio de sesión
            return "login";
        }
    }

    /**
     * Muestra el formulario para añadir una nueva actividad.
     * @param model Modelo para agregar atributos a la vista.
     * @param request Objeto HttpServletRequest para acceder a la sesión.
     * @return La vista para añadir una nueva actividad si el alumno está autenticado, de lo contrario, la vista de inicio de sesión.
     */
    @GetMapping("/new/{idalumno}")
    public String newActividad(Model model,HttpServletRequest request){
        HttpSession session=request.getSession();
        Alumno alumno = (Alumno) session.getAttribute("alumno");

        if(alumno != null){
            Long idAlumno = alumno.getIdalumno();
            //actividad.setAlumno(alumno);
            model.addAttribute("idalumno",idAlumno);
            return "anyadirActividad";
        }else{
            return "login";
        }
    }

    /**
     * Muestra el formulario para editar una actividad existente.
     * @param idActividad ID de la actividad a editar.
     * @param idAlumno ID del alumno asociado a la actividad.
     * @param fecha Fecha de la actividad.
     * @param tipo Tipo de actividad.
     * @param horas Horas dedicadas a la actividad.
     * @param actividad Descripción de la actividad.
     * @param observacion Observaciones sobre la actividad.
     * @param model Modelo para agregar atributos a la vista.
     * @return La vista para editar la actividad.
     */
    @GetMapping("/editar/{idActividad}")
    public String mostrarDetalleActividad(@PathVariable("idActividad") String idActividad,
                                          @RequestParam("idAlumno") Long idAlumno,
                                          @RequestParam("fecha")@DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha,
                                          @RequestParam("tipo") String tipo, @RequestParam("horas") Integer horas,
                                          @RequestParam("actividad") String actividad,
                                          @RequestParam("observacion") String observacion, Model model) {

        model.addAttribute("idActividad", idActividad);
        model.addAttribute("idAlumno", idAlumno);
        model.addAttribute("fecha", fecha);
        model.addAttribute("tipo", tipo);
        model.addAttribute("horas", horas);
        model.addAttribute("actividad", actividad);
        model.addAttribute("observacion", observacion);

        return "actualizarActividad";
    }

    /**
     * Maneja la edición de una actividad existente.
     * @param idActividad ID de la actividad a editar.
     * @param fecha Fecha de la actividad.
     * @param horas Horas dedicadas a la actividad.
     * @param tipo Tipo de actividad.
     * @param actividad Descripción de la actividad.
     * @param observacion Observaciones sobre la actividad.
     * @param request Objeto HttpServletRequest para acceder a la sesión.
     * @return Redirección a la página del alumno después de editar la actividad si está autenticado, de lo contrario, la vista de inicio de sesión.
     */
    @PostMapping("/editar/{idActividad}")
    public String editActivityPost(@PathVariable Long idActividad, @RequestParam String fecha, @RequestParam Integer horas,
                                   @RequestParam String tipo, @RequestParam String actividad, @RequestParam String observacion,
                                   HttpServletRequest request) {
        // Obtiene la sesión actual del usuario
        HttpSession session = request.getSession();
        // Verifica si hay un alumno en sesión
        Alumno alumnoSession = (Alumno) session.getAttribute("alumno");

        if (alumnoSession != null) { // Si hay un alumno en sesión
            // Busca la actividad en la base de datos por su ID
            Actividad actividadExistente = actividadRepository.findById(idActividad).orElse(null);

            // Verifica si la actividad existe
            if (actividadExistente != null) {
                // Verifica que los datos de la actividad no estén vacíos
                if (fecha != null && !fecha.isEmpty() && tipo != null && !tipo.isEmpty() && actividad != null && !actividad.isEmpty() && observacion != null) {

                    // Crea un formato de fecha para convertir la cadena de fecha en un objeto Date
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date fechaDate;
                    try {
                        // Convierte la cadena de fecha en un objeto Date
                        fechaDate = dateFormat.parse(fecha);
                    } catch (ParseException e) {
                        // Maneja la excepción en caso de error al analizar la fecha
                        e.printStackTrace();
                        // Redirige de vuelta a la página de añadir actividad si hay un error en la fecha
                        return "redirect:/editar/" + idActividad;
                    }

                    // Establece los nuevos valores para la actividad existente
                    actividadExistente.setFecha(fechaDate);
                    actividadExistente.setHoras(horas);
                    actividadExistente.setTipo(tipo);
                    actividadExistente.setActividad(actividad);
                    actividadExistente.setObservacion(observacion);

                    // Guarda la actividad actualizada en la base de datos
                    actividadRepository.save(actividadExistente);

                    // Redirige a la página del alumno después de guardar la actividad
                    return "redirect:/" + alumnoSession.getIdalumno();
                } else {
                    // Redirige de vuelta a la página de detalle de actividad si faltan datos
                    return "redirect:/editar/" + idActividad;
                }
            } else {
                // Si no se encuentra la actividad, redirige a la página de detalle de actividad
                return "redirect:/editar/" + idActividad;
            }
        } else {
            // Si no hay un alumno en sesión, redirige a la página de inicio de sesión
            return "login";
        }
    }

    /**
     * Muestra el formulario de inicio de sesión.
     *
     * @param modelo Modelo para la vista.
     * @return Vista del formulario de inicio de sesión.
     */
    @GetMapping("/login")
    public String getLogin(Model modelo){
        modelo.addAttribute("alumno",new Alumno());
        return "login";
    }

    /**
     * Maneja el proceso de inicio de sesión de un alumno.
     * @param alumno Objeto Alumno con los datos del alumno que intenta iniciar sesión.
     * @param request Objeto HttpServletRequest para acceder a la sesión.
     * @return Redirección a la página principal del alumno si el inicio de sesión es exitoso, de lo contrario, la vista de inicio de sesión.
     */
    @PostMapping("/succesfull")
    public String getAlumno(@ModelAttribute Alumno alumno, HttpServletRequest request){
        Boolean existencia = alumnoRepository.existsAlumnoByEmail(alumno.getEmail());
        System.out.println(existencia.toString());
        if(existencia){
            Alumno alumnoBBDD = alumnoRepository.getAlumnoByEmail(alumno.getEmail());
            if(alumnoBBDD.getContrasenya().equals(alumno.getContrasenya())){
                HttpSession s = request.getSession();
                s.setAttribute("alumno",alumnoBBDD);
                return "redirect:/"+ alumnoBBDD.getIdalumno();
            }else{
                return "redirect:/login";
            }
        }else{
            return "redirect:/login";
        }
    }

    /**
     * Elimina una actividad específica.
     * @param idActividad ID de la actividad a eliminar.
     * @param request Objeto HttpServletRequest para acceder a la sesión.
     * @return Redirección a la página del alumno después de eliminar la actividad si está autenticado, de lo contrario, la vista de inicio de sesión.
     */
    @PostMapping("/borrarActividad/{idActividad}")
    public String eliminaActividad(@PathVariable Long idActividad, HttpServletRequest request) {
        // Obtén la sesión actual del usuario
        HttpSession session = request.getSession();
        // Verifica si hay un alumno en sesión
        Alumno alumnoSession = (Alumno) session.getAttribute("alumno");

        if (alumnoSession != null) { // Si hay un alumno en sesión
            // Busca la actividad en la base de datos por su ID
            Optional<Actividad> actividadOptional = actividadRepository.findById(idActividad);
            // Verifica si la actividad existe
            if (actividadOptional.isPresent()) {
                // Elimina la actividad de la base de datos
                //actividadRepository.delete(actividadOptional.get());
                actividadRepository.delete(actividadOptional.get());
            }
            // Redirige a la página del alumno después de eliminar la actividad
            return "redirect:/" + alumnoSession.getIdalumno();
        } else {
            // Si no hay un alumno en sesión, redirige a la página de inicio de sesión
            return "login";
        }
    }
    /**
     * Cierra la sesión del usuario actual.
     * @param model Modelo para agregar atributos a la vista.
     * @param request Objeto HttpServletRequest para acceder a la sesión.
     * @return La vista de inicio de sesión.
     */
    @GetMapping("logout")
    public String logout(Model model, HttpServletRequest request)  {
        HttpSession session = request.getSession();
        session.invalidate();
        model.addAttribute("alumno",new Alumno());
        return "login";

    }
}
