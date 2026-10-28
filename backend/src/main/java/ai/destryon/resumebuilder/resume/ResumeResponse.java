package ai.destryon.resumebuilder.resume;

import java.time.Instant;
import java.util.UUID;

public record ResumeResponse(
    UUID id,
    UUID candidateProfileId,
    String title,
    String sourceFilename,
    String sourceContentType,
    Long fileSizeBytes,
    String parseStatus,
    String parseError,
    String extractedText,
    Instant createdAt
) {
    static ResumeResponse from(Resume resume) {
        return new ResumeResponse(
            resume.getId(),
            resume.getCandidateProfile().getId(),
            resume.getTitle(),
            resume.getSourceFilename(),
            resume.getSourceContentType(),
            resume.getFileSizeBytes(),
            resume.getParseStatus(),
            resume.getParseError(),
            resume.getSourceText(),
            resume.getCreatedAt()
        );
    }
}
