package hexlet.code.dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Setter
@Getter
public class UserUpdateDTO {

    @Email
    @NotNull
    private JsonNullable<String> email;

    private JsonNullable<String> firstName;
    private JsonNullable<String> lastName;

    @NotBlank
    @Size(min = 3)
    private JsonNullable<String> password;
}
