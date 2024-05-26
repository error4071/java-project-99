package hexlet.code.component;

import hexlet.code.dto.Label.LabelCreateDTO;
import hexlet.code.dto.TaskStatus.TaskStatusCreateDTO;
import hexlet.code.dto.User.UserCreateDTO;
import hexlet.code.mapper.LabelMapper;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.mapper.UserMapper;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;

    private final TaskStatusRepository taskStatusRepository;

    private final LabelRepository labelRepository;

    private final UserMapper userMapper;

    private final TaskStatusMapper taskStatusMapper;

    private final LabelMapper labelMapper;

    private PasswordEncoder passwordEncoder;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        var userData = new UserCreateDTO();
        userData.setEmail("hexlet@example.com");
        userData.setPassword(passwordEncoder.encode("qwerty"));
        var user = userMapper.map(userData);
        userRepository.save(user);
    }

    public void addSlug() {
        List<String> defaultSlug = List.of("draft", "to_review", "to_be_fixed", "to_publish", "published");
        defaultSlug.forEach(slug -> {
            var statusData = new TaskStatusCreateDTO();
            String[] arr = slug.split("_");
            String data = arr[0].substring(0, 1).toUpperCase() + arr[0].substring(1);
            var name = new StringBuilder(String.valueOf(data));

            if (arr.length > 1) {
                for (var element: arr) {
                    name.append(" ").append(element);
                }
            }

            statusData.setName(name.toString());
            statusData.setSlug(slug.toString());
            var status = taskStatusMapper.map(statusData);
            taskStatusRepository.save(status);
        });
    }

    public void addLabel() {
        List<String> labels = List.of("feature", "bug");
        labels.forEach(name -> {
            var labelData = new LabelCreateDTO();
            labelData.setName(name);
            var label = labelMapper.map(labelData);
            labelRepository.save(label);
        });
    }
}
