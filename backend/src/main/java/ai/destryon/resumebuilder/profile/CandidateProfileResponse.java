package ai.destryon.resumebuilder.profile;

import java.time.Instant;
import java.util.UUID;

public record CandidateProfileResponse(UUID id, String fullName, String email, String phone,
        String location, String headline, String summary, String skillsText, String experienceText,
        String educationText, String projectsText, Instant createdAt, Instant updatedAt) {
    static CandidateProfileResponse from(CandidateProfile p) {
        return new CandidateProfileResponse(p.getId(), p.getFullName(), p.getEmail(), p.getPhone(),
            p.getLocation(), p.getHeadline(), p.getSummary(), p.getSkillsText(), p.getExperienceText(),
            p.getEducationText(), p.getProjectsText(), p.getCreatedAt(), p.getUpdatedAt());
    }
}
