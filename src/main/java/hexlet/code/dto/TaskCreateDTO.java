package hexlet.code.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskCreateDTO {

    private Integer index;

    private String assignee;

    @NotBlank
    @Size(min = 1)
    private String name;

    private String description;

    @NotBlank
    private String taskStatus;
}
