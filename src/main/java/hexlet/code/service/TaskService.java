package hexlet.code.service;

import hexlet.code.dto.Task.TaskCreateDTO;
import hexlet.code.dto.Task.TaskDTO;
import hexlet.code.dto.TaskFilterDTO;
import hexlet.code.dto.Task.TaskUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.specification.TaskSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    private final TaskStatusRepository taskStatusRepository;

    private final TaskMapper taskMapper;

    private final TaskSpecification specBuilder;

    public List<TaskDTO> getAll(TaskFilterDTO params) {
        var spec = specBuilder.build(params);
        var tasks = taskRepository.findAll((Sort) spec);
        return tasks.stream()
                .map(taskMapper::map)
                .toList();
    }

    public TaskDTO create(TaskCreateDTO taskData) {
        var task = taskMapper.map(taskData);

        var assigneeId = taskData.getAssigneeId();

        if (assigneeId != null) {
            var assignee = userRepository.findById(assigneeId).orElse(null);
            task.setAssignee(assignee);
        }

        var statusSlug = taskData.getStatus();
        var taskStatus = taskStatusRepository.findBySlug(statusSlug).orElse(null);

        task.setTaskStatus(taskStatus);

        taskRepository.save(task);
        return taskMapper.map(task);
    }

    public TaskDTO findById(Long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
        return taskMapper.map(task);
    }

    public TaskDTO update(TaskUpdateDTO taskData, Long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));

        taskMapper.update(taskData, task);
        var statusSlug = taskData.getStatus();

        if (statusSlug != null) {
            var taskStatus = taskStatusRepository.findBySlug((statusSlug).get()).orElse(null);
            task.setTaskStatus(taskStatus);
        }

        taskRepository.save(task);
        return taskMapper.map(task);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
