package com.example.tfgdefinitivo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TfgDefinitivoApplication extends SpringBootServletInitializer {

    private static final Class<TfgDefinitivoApplication> applicationClass = TfgDefinitivoApplication.class;
    private static final Logger log = LoggerFactory.getLogger(applicationClass);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TfgDefinitivoApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(TfgDefinitivoApplication.class, args);
    }

}

