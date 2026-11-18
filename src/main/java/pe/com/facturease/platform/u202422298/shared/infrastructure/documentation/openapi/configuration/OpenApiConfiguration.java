package pe.com.facturease.platform.u202422298.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

/** Configures API documentation and Swagger UI metadata.
 * @author Replace With Your Full Name
 */
@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI factureaseOpenApi() {
        return new OpenAPI()
            .info(new Info().title("Facturease Peru API")
                    .description("Corporate electronic invoice registration API")
                    .version("1.0.0"))
            .servers(List.of(new Server().url("http://localhost:8097")
                    .description("Local development")));
    }
}
