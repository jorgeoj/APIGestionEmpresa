package com.example.apigestionpracticasempresa.controllers;

import com.example.apigestionpracticasempresa.model.Actividad;
import com.example.apigestionpracticasempresa.model.Alumno;
import com.example.apigestionpracticasempresa.repositories.ActividadRepository;
import com.example.apigestionpracticasempresa.repositories.AlumnoRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/")
public class AlumnoWebController {

    // TODO Mirar los html por si se quieren cambiar cosas
    // TODO hacer html editarActividad
    // TODO Comprobar que funcionan las cosas

    @Autowired
    private ActividadRepository actividadRepository;
    @Autowired
    private AlumnoRepository alumnoRepository;

    @GetMapping("/")
    public String volverLogin(){
        return "redirect:/login";
    }

    // TODO Comprobar si esta bien
    @GetMapping("/{idalumno}")
    public String getActividadesByAlumno(@PathVariable Long idalumno, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Alumno alumnoSession = (Alumno) session.getAttribute("alumno");

        if(alumnoSession!=null){
            Alumno alumno2 = alumnoSession;
            model.addAttribute("actividades",actividadRepository.getAllByAlumno(alumno2));
            System.out.println(actividadRepository.getAllByAlumno(alumno2));
            return "mainViewAlumno";
        }else{
            model.addAttribute("alumno",new Alumno());
            return "login";
        }
    }

    // TODO Probar si funciona
    @PostMapping("/new/{idalumno}")
    public String newActividad(@PathVariable Long idAlumno, @RequestParam String fecha, @RequestParam Integer horas, @RequestParam String tipo, @RequestParam String actividad,
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
                    return "redirect:/new/" + idAlumno;
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
                return "redirect:/" + idAlumno;
            } else {
                // Redirige de vuelta a la página de añadir actividad si faltan datos
                return "redirect:/new/" + idAlumno;
            }
        } else {
            // Si no hay un alumno en sesión, redirige a la página de inicio de sesión
            return "login";
        }
    }
    /*
    public String newActividad(@PathVariable Long idalumno,@ModelAttribute Actividad actividad, HttpServletRequest request, Model model){
        HttpSession session=request.getSession();
        Alumno alumno = (Alumno) session.getAttribute("alumno");
        System.out.println(actividad);

        if(alumno!= null && (!(actividad.getActividad().equals("")) && !(actividad.getFecha().equals(""))) &&
                actividad.getTipo() !=null && actividad.getHoras() != null){
            System.out.println(actividad);
            actividad.setAlumno(alumno);
            actividadRepository.save(actividad);
            return "redirect:/"+idalumno ;
        }else if(alumno == null){
            model.addAttribute("alumno",new Alumno());
            return "login";
        } else if (actividad.getActividad().equals("") || actividad.getFecha().equals("") ||
                actividad.getTipo() == null || actividad.getHoras() == null) {
            return "redirect:/new/";
        } else{
            return "";
        }
    }
    */

    @GetMapping("/new/{idalumno}")
    public String newActividad(Model model,HttpServletRequest request){
        //Actividad actividad = new Actividad();
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

    // TODO Probar si funciona
    @GetMapping("/edit/{idActividad}")
    public String editarActividad(@PathVariable Long idActividad, Model model, HttpServletRequest request){
        HttpSession session=request.getSession();
        Alumno alumno = (Alumno) session.getAttribute("alumno");
        if(alumno==null){
            model.addAttribute("alumno",new Alumno());
            return "login";
        }else{

            model.addAttribute("actividad",actividadRepository.findById(idActividad).get());
            return "anyadirActividad";
        }

    }

    // TODO Probar si funciona
    @PostMapping("/edit/{idActividad}")
    public String editActivityPost(@PathVariable Long idActividad, @ModelAttribute Actividad actividad,HttpServletRequest request,Model model){
        HttpSession sesion=request.getSession();
        Alumno alumno = (Alumno) sesion.getAttribute("alumno");
        if(alumno != null && !actividad.getActividad().equals("") && !actividad.getFecha().equals("")
                && actividad.getHoras() != null && actividad.getTipo() != null){
            actividad.setAlumno(alumno);
            actividadRepository.save(actividad);
            return "redirect:/"+Long.valueOf( (alumno).getIdalumno());
        }else if(alumno==null){
            model.addAttribute("alumno",new Alumno());
            return "login";
        } else if(actividad.getActividad().equals("") || actividad.getFecha().equals("") ||
                actividad.getTipo() == null || actividad.getHoras() == null){
            return "redirect:/edit/"+actividad.getIdactividad();
        } else{
            return "";
        }
    }

    // TODO Probar si funciona
    @GetMapping("/login")
    public String getLogin(Model modelo){
        modelo.addAttribute("alumno",new Alumno());
        return "login";
    }

    // TODO Probar si funciona
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

    // TODO Probar si funciona
    @PostMapping("/borrarActividad/{id}")
    public String borrarActividad(@PathVariable Long id, HttpServletRequest request,Model model){
        HttpSession session=request.getSession();
        Alumno alumno = (Alumno) session.getAttribute("alumno");

        if(alumno != null && id != null){
            Actividad actividad = actividadRepository.findById(id).get();
            actividadRepository.delete(actividad);
            return "redirect:/"+alumno.getIdalumno();
        } else if (alumno == null) {
            model.addAttribute("alumno",new Alumno());
            return "login";
        } else{
            Actividad actividad = new Actividad();
            actividad.setAlumno(alumno);
            model.addAttribute("actividad",actividad);
            return "anyadirActividad";
        }


    }

    // TODO Probar si funciona
    @GetMapping("logout")
    public String logout(Model model, HttpServletRequest request)  {
        HttpSession session = request.getSession();
        session.invalidate();
        model.addAttribute("alumno",new Alumno());
        return "login";

    }
}
