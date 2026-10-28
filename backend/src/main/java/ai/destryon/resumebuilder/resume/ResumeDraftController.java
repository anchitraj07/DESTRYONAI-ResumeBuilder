package ai.destryon.resumebuilder.resume;

import java.util.UUID;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resumes")
public class ResumeDraftController {
    private final ResumeDraftService draftService;
    public ResumeDraftController(ResumeDraftService draftService) { this.draftService = draftService; }

    @PostMapping("/{id}/profile-draft")
    public CandidateProfileDraft createDraft(@PathVariable UUID id) {
        return draftService.createDraft(id);
    }
}
