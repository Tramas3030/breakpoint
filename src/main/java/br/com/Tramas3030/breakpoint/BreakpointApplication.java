package br.com.Tramas3030.breakpoint;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Breakpoint",
				description = "API do aplicativo Breakpoint",
				version = "1"
		)
)
public class BreakpointApplication {

	public static void main(String[] args) {
		SpringApplication.run(BreakpointApplication.class, args);
	}

}
