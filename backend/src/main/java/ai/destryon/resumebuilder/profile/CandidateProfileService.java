package ai.destryon.resumebuilder.profile;

import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ai.destryon.resumebuilder.resume.ResourceNotFoundException;

@Service
public class CandidateProfileService {
    private final CandidateProfileRepository repository;
    public CandidateProfileService(CandidateProfileRepository repository) { this.repository = repository; }

    @Transactional(readOnly = true)
    public CandidateProfileResponse get(UUID id) {
        return repository.findById(id).map(CandidateProfileResponse::from)
            .orElseThrow(() -> new ResourceNotFoundException("Candidate profile not found."));
    }

    @Transactional
    public CandidateProfileResponse update(UUID id, CandidateProfileRequest request) {
        CandidateProfile p = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Candidate profile not found."));
        p.setFullName(request.fullName());
        p.setEmail(request.email());
        p.setPhone(request.phone());
        p.setLocation(request.location());
        p.setHeadline(request.headline());
        p.setSummary(request.summary());
        p.setSkillsText(request.skillsText());
        p.setExperienceText(request.experienceText());
        p.setEducationText(request.educationText());
        p.setProjectsText(request.projectsText());
        return CandidateProfileResponse.from(repository.save(p));
    }
}
