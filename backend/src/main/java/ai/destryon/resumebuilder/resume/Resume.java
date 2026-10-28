package ai.destryon.resumebuilder.resume;

import ai.destryon.resumebuilder.profile.CandidateProfile;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "resumes")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "candidate_profile_id", nullable = false)
    private CandidateProfile candidateProfile;
    @Column(nullable = false, length = 200) private String title;
    @Column(name = "source_filename", length = 255) private String sourceFilename;
    @Column(name = "source_content_type", length = 120) private String sourceContentType;
    @Column(name = "source_text", columnDefinition = "text") private String sourceText;
    @Column(name = "source_file", columnDefinition = "bytea") private byte[] sourceFile;
    @Column(name = "file_size_bytes") private Long fileSizeBytes;
    @Column(name = "parse_status", nullable = false, length = 20) private String parseStatus = "PENDING";
    @Column(name = "parse_error", columnDefinition = "text") private String parseError;
    @Column(name = "created_at", nullable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;

    public Resume() {}
    @PrePersist void onCreate() { Instant now = Instant.now(); createdAt = now; updatedAt = now; }
    @PreUpdate void onUpdate() { updatedAt = Instant.now(); }
    public UUID getId() { return id; }
    public CandidateProfile getCandidateProfile() { return candidateProfile; }
    public void setCandidateProfile(CandidateProfile candidateProfile) { this.candidateProfile = candidateProfile; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSourceFilename() { return sourceFilename; }
    public void setSourceFilename(String sourceFilename) { this.sourceFilename = sourceFilename; }
    public String getSourceContentType() { return sourceContentType; }
    public void setSourceContentType(String sourceContentType) { this.sourceContentType = sourceContentType; }
    public String getSourceText() { return sourceText; }
    public void setSourceText(String sourceText) { this.sourceText = sourceText; }
    public byte[] getSourceFile() { return sourceFile; }
    public void setSourceFile(byte[] sourceFile) { this.sourceFile = sourceFile; }
    public Long getFileSizeBytes() { return fileSizeBytes; }
    public void setFileSizeBytes(Long fileSizeBytes) { this.fileSizeBytes = fileSizeBytes; }
    public String getParseStatus() { return parseStatus; }
    public void setParseStatus(String parseStatus) { this.parseStatus = parseStatus; }
    public String getParseError() { return parseError; }
    public void setParseError(String parseError) { this.parseError = parseError; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
