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
        CandidateProfile profile = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Candidate profile not found."));
        profile.setFullName(request.fullName());
        profile.setEmail(request.email());
        profile.setPhone(request.phone());
        profile.setLocation(request.location());
        profile.setHeadline(request.headline());
        profile.setSummary(request.summary());
        return CandidateProfileResponse.from(repository.save(profile));
    }
}
