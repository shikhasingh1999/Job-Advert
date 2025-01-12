package com.example.jobService.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI (@Value("${application-title}") String title,
                            @Value("${application-description}") String description,
                            @Value("${application-version}") String version,
                            @Value("${application-license}") String license) {

        return new OpenAPI()
                .info(new Info()
                    .title(title)
                    .description(description)
                    .version(version)
                    .license(new License().name(license)))
                .addSecurityItem(new SecurityRequirement()  //  define security requirements for the single operation (when applied at method level) or for all operations of a class (when applied at class level).
                        .addList("Bearer Authentication"))  // name of the security to be applied
                .components(new Components()    // add a new component for security
                        .addSecuritySchemes("Bearer Authentication", createAPIKeyScheme())  // add the security scheme to the component which defines what type of security to be applied
                        // security scheme name is the key -> eg "Bearer Authentication"
                        // then define that securityScheme with type, scheme, format etc.
                );

    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .scheme("bearer")
                .bearerFormat("JWT")
                .type(SecurityScheme.Type.HTTP);

    }
}
