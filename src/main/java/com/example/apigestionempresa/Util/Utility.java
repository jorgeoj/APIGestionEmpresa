package com.example.apigestionempresa.Util;

import java.io.IOException;

/**
 * Clase de utilidad para funciones diversas.
 */
public class Utility {

    /**
     * Método para abrir automáticamente el navegador web con una URL específica.
     * Selecciona el navegador según el Sistema Operativo.
     */
    public static void launchWeb(){
        //Obtengo el nombre del Sistema Operativo
        String os = System.getProperty("os.name").toLowerCase();
        try{
            // Abro el navegador web según el Sistema Operativo
            if (os.contains("win")){
                Runtime.getRuntime().exec("cmd /c start chrome http://localhost:8080/login");
            }else{
                Runtime.getRuntime().exec("open -a Safari http://localhost:8080/login");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
