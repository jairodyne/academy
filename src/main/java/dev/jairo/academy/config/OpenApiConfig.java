package dev.jairo.academy.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Documentation available at http://localhost:8080/swagger-ui/index.html
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Academy API")
                        .description(
                                """
                                Academy management API, including :
                                
                                - Cadastro de Alunos
                                - Matrículas e Planos
                                - Controle Financeiro
                                - Relatórios gerenciais
                                
                                Developed with Spring Boot for educational purposes.
                                """
                        )
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Jairo Carvalho")
                                .email("jairodyne@gmail.com")
                        )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0"))
                )
                .servers(List.of(new Server()
                        .url("http://localhost:8080")
                        .description("Local Server")))
                .externalDocs(new ExternalDocumentation().description("Project Documentation").url("https://github.com/jairodyne/academy"));
    }
}
