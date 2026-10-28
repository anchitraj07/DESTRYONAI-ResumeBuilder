package ai.destryon.resumebuilder.profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record CandidateProfileRequest(
    @Size(max = 160) String fullName,
    @Email @Size(max = 254) String email,
    @Size(max = 40) String phone,
    @Size(max = 160) String location,
    @Size(max = 240) String headline,
    @Size(max = 10000) String summary
) {}
