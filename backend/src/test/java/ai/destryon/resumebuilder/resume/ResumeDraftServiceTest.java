package ai.destryon.resumebuilder.resume;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import java.util.UUID;
import ai.destryon.resumebuilder.profile.CandidateProfile;
import org.junit.jupiter.api.Test;

class ResumeDraftServiceTest {
    @Test
    void extractsContactAndNamedSectionsAsEditableDraft() {
        UUID profileId = UUID.randomUUID();
        UUID resumeId = UUID.randomUUID();
        CandidateProfile profile = mock(CandidateProfile.class);
        when(profile.getId()).thenReturn(profileId);
        Resume resume = mock(Resume.class);
        when(resume.getId()).thenReturn(resumeId);
        when(resume.getCandidateProfile()).thenReturn(profile);
        when(resume.getParseStatus()).thenReturn("PARSED");
        when(resume.getSourceText()).thenReturn("""
            Jordan Lee
            Java Backend Developer
            jordan.lee@example.com
            +91 98765 43210
            Summary
            Backend engineer with Spring Boot experience.
            Skills
            Java, Spring Boot, PostgreSQL
            Experience
            Example Corp - Backend Developer
            Built REST APIs.
            Education
            B.Tech, Computer Science
            Projects
            Resume Builder
            """);
        ResumeRepository repository = mock(ResumeRepository.class);
        when(repository.findById(resumeId)).thenReturn(Optional.of(resume));

        CandidateProfileDraft draft = new ResumeDraftService(repository).createDraft(resumeId);

        assertEquals("jordan.lee@example.com", draft.email());
        assertTrue(draft.skillsText().contains("Java, Spring Boot, PostgreSQL"));
        assertTrue(draft.experienceText().contains("Built REST APIs."));
        assertTrue(draft.educationText().contains("B.Tech, Computer Science"));
        assertTrue(draft.projectsText().contains("Resume Builder"));
        assertFalse(draft.reviewNotes().isEmpty());
    }
}
