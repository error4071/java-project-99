package hexlet.code.controller.api;

import hexlet.code.dto.Label.LabelCreateDTO;
import hexlet.code.dto.Label.LabelDTO;
import hexlet.code.dto.Label.LabelUpdateDTO;
import hexlet.code.mapper.LabelMapper;
import hexlet.code.repository.LabelRepository;
import hexlet.code.service.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class LabelController {

    private final LabelRepository labelRepository;
    private final LabelService labelService;
    private final LabelMapper labelMapper;

    @GetMapping("/labels")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Show all labels")
    ResponseEntity<List<LabelDTO>> index(@RequestParam(defaultValue = "10") Integer limit) {
        var label = labelRepository.findAll();
        var result =  label.stream()
                .map(x -> labelMapper.map(x))
                .toList();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(label.size()))
                .body(result);
    }

    @GetMapping("/labels/{id}")
    @Operation(summary = "Show label by id")
    @ResponseStatus(HttpStatus.OK)
    public LabelDTO show(@PathVariable Long id) {
        return labelService.findById(id);
    }

    @PostMapping("/labels")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new label")
    public LabelDTO create(@RequestBody @Valid LabelCreateDTO labelCreateDTO) {
        return labelService.create(labelCreateDTO);
    }

    @PutMapping("/labels/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update label")
    public LabelDTO update(@PathVariable Long id, @RequestBody LabelUpdateDTO labelUpdateDTO) {
        return labelService.update(id, labelUpdateDTO);
    }

    @DeleteMapping("/labels/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete label")
    public void delete(@PathVariable Long id) {
        labelRepository.deleteById(id);
    }
}
