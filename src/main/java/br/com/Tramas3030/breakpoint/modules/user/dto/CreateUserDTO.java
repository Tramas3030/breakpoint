package br.com.Tramas3030.breakpoint.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CreateUserDTO(
    @NotBlank(message = "O campo [nome] não pode ficar em branco")
    @Schema(example = "Simone Simons")
    String name,

    @NotBlank(message = "O campo [email] não pode ficar em branco")
    @Email(message = "O campo [email] deve conter um email válido")
    @Schema(example = "simonesimons@gmail.com")
    String email,

    @NotBlank(message = "O campo [senha] não pode ficar em branco")
    @Length(min = 5, max = 100, message = "A senha deve conter entre (5) e (100) caracteres")
    @Schema(example = "12345")
    String password
) {
}
