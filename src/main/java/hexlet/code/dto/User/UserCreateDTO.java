package hexlet.code.dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDTO {

    private String firstName;
    private String lastName;

    @Email
    private String email;

    @Size(min = 3, max = 100)
    @NotBlank
    private String password;
}
