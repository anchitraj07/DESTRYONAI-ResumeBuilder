package ai.destryon.resumebuilder.resume;

import java.util.List;
import java.util.UUID;

public record CandidateProfileDraft(
    UUID resumeId,
    UUID candidateProfileId,
    String fullName,
    String email,
    String phone,
    String headline,
    String summary,
    String skillsText,
    String experienceText,
    String educationText,
    String projectsText,
    List<String> reviewNotes
) {}
