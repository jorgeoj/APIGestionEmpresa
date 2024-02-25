package com.example.apigestionempresa;

import com.example.apigestionempresa.Util.Utility;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGestionEmpresaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGestionEmpresaApplication.class, args);
        Utility.launchWeb();
    }

}
