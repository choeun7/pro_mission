package com.pingpong.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        License license = new License();
        license.setName("PingPong");

        Info info = new Info()
                .title("\"프로그라피 API문서\"")
                .description("탁구 미션 API 문서 입니다.")
                .version("v0.0.1")
                .license(license);;

        return new OpenAPI()
                .info(info);
    }

}

