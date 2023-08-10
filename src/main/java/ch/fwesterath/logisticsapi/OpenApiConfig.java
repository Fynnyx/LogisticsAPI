package ch.fwesterath.logisticsapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Logistic API",
                version = "1.0",
                description = "This is the API for any logisitc project.",
                license = @io.swagger.v3.oas.annotations.info.License(name = "MIT License", url = "https://opensource.org/licenses/MIT"),
                termsOfService = ""
        ),
        servers = {
                @io.swagger.v3.oas.annotations.servers.Server(
                        description = "Production Server",
                        url = "https://api.logisticapi.fwesterath.ch"
                ),
                @io.swagger.v3.oas.annotations.servers.Server(
                        description = "Development Server",
                        url = "http://localhost:8080"
                )
        }
)
public class OpenApiConfig {
    // Configuration code will be added here
}