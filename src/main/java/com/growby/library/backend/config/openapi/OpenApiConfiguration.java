package com.growby.library.backend.config.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    public static final String OPENAPI_TITLE = "API Library";
    public static final String OPENAPI_DESCRIPTION = "API RESTful of Library";
    public static final String OPENAPI_VERSION = "1.0";

    @Bean
    public OpenAPI libraryOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(OPENAPI_TITLE)
                        .description(OPENAPI_DESCRIPTION)
                        .version(OPENAPI_VERSION));
    }
}
