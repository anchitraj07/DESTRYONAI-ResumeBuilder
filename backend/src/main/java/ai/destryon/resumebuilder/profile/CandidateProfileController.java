package ai.destryon.resumebuilder.profile;

import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
public class CandidateProfileController {
    private final CandidateProfileService service;
    public CandidateProfileController(CandidateProfileService service) { this.service = service; }

    @GetMapping("/{id}")
    public CandidateProfileResponse get(@PathVariable UUID id) { return service.get(id); }

    @PutMapping("/{id}")
    public CandidateProfileResponse update(@PathVariable UUID id, @Valid @RequestBody CandidateProfileRequest request) {
        return service.update(id, request);
    }
}
