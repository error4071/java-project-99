
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

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final TaskStatusRepository taskStatusRepository;

    private final TaskStatusMapper taskStatusMapper;

    private final LabelRepository labelRepository;

    private final LabelMapper labelMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        addSuperUser();
        addDefaultSlugs();
        addDefaultLabels();
    }

    public void addSuperUser() {
        var userData = new UserCreateDTO();
        userData.setEmail("hexlet@example.com");
        userData.setPassword("qwerty");
        var user = userMapper.map(userData);
        var hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPasswordDigest(hashedPassword);
        userRepository.save(user);
    }

    public void addDefaultSlugs() {
        List<String> defaultSlugs = List.of("draft", "to_review", "to_be_fixed", "to_publish", "published");
        defaultSlugs.forEach(slug -> {
            var statusData = new TaskStatusCreateDTO();
            String[] arr = slug.split("_");
            String first = arr[0].substring(0, 1).toUpperCase() + arr[0].substring(1);
            var name = new StringBuilder(first);

            if (arr.length > 1) {
                for (var element: arr) {
                    name.append(" ").append(element);
                }
            }

            statusData.setName(name.toString());
            statusData.setSlug(slug);
            var status = taskStatusMapper.map(statusData);
            taskStatusRepository.save(status);
        });
    }

    public void addDefaultLabels() {
        List<String> defaultLabels = List.of("feature", "bug");
        defaultLabels.forEach(name -> {
            var labelData = new LabelCreateDTO();
            labelData.setName(name);
            var label = labelMapper.map(labelData);
            labelRepository.save(label);
        });
    }
}
