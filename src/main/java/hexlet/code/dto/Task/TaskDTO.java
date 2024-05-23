package hexlet.code.dto.Task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class TaskDTO {

    private Long id;
    private Integer index;
    private Date createdAt;

    @JsonProperty("assignee_id")
    private Long assigneeId;

    private String title;
    private String content;
    private String status;

    private Set<Long> taskLabelIds;
}
