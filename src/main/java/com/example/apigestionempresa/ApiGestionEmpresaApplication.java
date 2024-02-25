package com.example.apigestionempresa;

import com.example.apigestionempresa.Util.Utility;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal que inicia la aplicación de gestión de empresa.
 */
@SpringBootApplication
public class ApiGestionEmpresaApplication {

    /**
     * Método principal que inicia la aplicación Spring Boot y abre automáticamente el navegador web.
     * @param args Argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        // Inicia la aplicación Spring Boot
        SpringApplication.run(ApiGestionEmpresaApplication.class, args);
        // Abre automáticamente el navegador web
        Utility.launchWeb();
    }

}
