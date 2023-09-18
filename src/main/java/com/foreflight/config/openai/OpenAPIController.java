package com.foreflight.config.openai;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Hidden
@RestController
public class OpenAPIController {

    @GetMapping("/")
    public RedirectView redirectRootToSwaggerUi() {
        return new RedirectView("/swagger-ui/index.html");
    }

    @GetMapping("/v1")
    public RedirectView redirectVersionToSwaggerUi() {
        return new RedirectView("/swagger-ui/index.html");
    }
}
