package hexlet.code.dto.Task;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskCreateDTO {

    private Integer index;

    @JsonProperty("assignee_id")
    private Long assigneeId;

    @NotBlank
    private String title;
    private String content;

    @NotNull
    private String status;

    private Set<Long> taskLabelIds;
}
