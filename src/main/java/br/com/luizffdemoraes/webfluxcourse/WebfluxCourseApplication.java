package br.com.luizffdemoraes.webfluxcourse;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "WebFlux Course API",
				version = "1.0.0",
				description = "API using WebFlux to register users in the mongodb database."
		),
		servers = @Server(url = "http://localhost:8080", description = "Local Server")
)
public class WebfluxCourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxCourseApplication.class, args);
	}

}
