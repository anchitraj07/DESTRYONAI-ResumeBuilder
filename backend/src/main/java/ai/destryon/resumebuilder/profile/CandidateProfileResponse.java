package ai.destryon.resumebuilder.profile;

import java.time.Instant;
import java.util.UUID;

public record CandidateProfileResponse(UUID id, String fullName, String email, String phone,
        String location, String headline, String summary, Instant createdAt, Instant updatedAt) {
    static CandidateProfileResponse from(CandidateProfile profile) {
        return new CandidateProfileResponse(profile.getId(), profile.getFullName(), profile.getEmail(),
            profile.getPhone(), profile.getLocation(), profile.getHeadline(), profile.getSummary(),
            profile.getCreatedAt(), profile.getUpdatedAt());
    }
}
