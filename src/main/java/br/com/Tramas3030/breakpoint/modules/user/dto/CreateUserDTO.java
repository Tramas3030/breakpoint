package br.com.Tramas3030.breakpoint.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CreateUserDTO(
    @NotBlank(message = "O campo [nome] não pode ficar em branco")
    String name,

    @NotBlank(message = "O campo [email] não pode ficar em branco")
    @Email(message = "O campo [email] deve conter um email válido")
    String email,

    @NotBlank(message = "O campo [senha] não pode ficar em branco")
    @Length(min = 5, max = 100, message = "A senha deve conter entre (5) e (100) caracteres")
    String password
) {
}
