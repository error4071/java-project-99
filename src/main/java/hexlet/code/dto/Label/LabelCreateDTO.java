package hexlet.code.dto.Label;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabelCreateDTO {

    @Size(min = 3, max = 1000)
    private String name;
}
