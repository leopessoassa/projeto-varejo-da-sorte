package com.virtualize.varejodasorte.api.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/")
@Profile("dev-swagger")
@Hidden
public class RedirectController {
    
    private static final String SWAGGER_UI_HTML = "swagger-ui";
    
    @Value("${server.servlet.context-path:}")
    private String CONTEXT_PATH;

    @GetMapping("/")
    public RedirectView redirectPath(RedirectAttributes attributes) {
        return new RedirectView(CONTEXT_PATH + SWAGGER_UI_HTML);
    }
    
    @GetMapping("/calamidade-api")
    public RedirectView redirectApi(RedirectAttributes attributes) {
        return new RedirectView(SWAGGER_UI_HTML);
    }

}
