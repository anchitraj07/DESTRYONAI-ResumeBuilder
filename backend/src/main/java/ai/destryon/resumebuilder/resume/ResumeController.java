package ai.destryon.resumebuilder.resume;

import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resumes")
@Validated
public class ResumeController {
    private final ResumeService resumeService;
    public ResumeController(ResumeService resumeService) { this.resumeService = resumeService; }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResumeResponse upload(@RequestPart("file") @NotNull MultipartFile file) throws IOException {
        return resumeService.upload(file);
    }

    @GetMapping("/{id}")
    public ResumeResponse get(@PathVariable UUID id) { return resumeService.get(id); }
}
