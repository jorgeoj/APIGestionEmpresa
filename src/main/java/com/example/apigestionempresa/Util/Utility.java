package com.example.apigestionpracticasempresa.Util;

import java.io.IOException;

public class Utility {
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
