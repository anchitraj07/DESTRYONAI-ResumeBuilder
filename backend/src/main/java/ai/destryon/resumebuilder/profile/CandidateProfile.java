package ai.destryon.resumebuilder.profile;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "candidate_profiles")
public class CandidateProfile {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "full_name", length = 160) private String fullName;
    @Column(length = 254) private String email;
    @Column(length = 40) private String phone;
    @Column(length = 160) private String location;
    @Column(length = 240) private String headline;
    @Column(columnDefinition = "text") private String summary;
    @Column(name = "skills_text", columnDefinition = "text") private String skillsText;
    @Column(name = "experience_text", columnDefinition = "text") private String experienceText;
    @Column(name = "education_text", columnDefinition = "text") private String educationText;
    @Column(name = "projects_text", columnDefinition = "text") private String projectsText;
    @Column(name = "created_at", nullable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;

    public CandidateProfile() {}
    @PrePersist void onCreate() { Instant now = Instant.now(); createdAt = now; updatedAt = now; }
    @PreUpdate void onUpdate() { updatedAt = Instant.now(); }
    public UUID getId() { return id; }
    public String getFullName() { return fullName; }
    public void setFullName(String v) { fullName = v; }
    public String getEmail() { return email; }
    public void setEmail(String v) { email = v; }
    public String getPhone() { return phone; }
    public void setPhone(String v) { phone = v; }
    public String getLocation() { return location; }
    public void setLocation(String v) { location = v; }
    public String getHeadline() { return headline; }
    public void setHeadline(String v) { headline = v; }
    public String getSummary() { return summary; }
    public void setSummary(String v) { summary = v; }
    public String getSkillsText() { return skillsText; }
    public void setSkillsText(String v) { skillsText = v; }
    public String getExperienceText() { return experienceText; }
    public void setExperienceText(String v) { experienceText = v; }
    public String getEducationText() { return educationText; }
    public void setEducationText(String v) { educationText = v; }
    public String getProjectsText() { return projectsText; }
    public void setProjectsText(String v) { projectsText = v; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
