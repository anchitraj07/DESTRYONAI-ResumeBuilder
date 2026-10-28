package ai.destryon.resumebuilder.resume;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResumeDraftService {
    private static final Pattern EMAIL = Pattern.compile("(?i)\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}\\b");
    private static final Pattern PHONE = Pattern.compile("(?<!\\w)(?:\\+?\\d[\\d ()-]{7,}\\d)(?!\\w)");
    private static final List<String> SECTION_HEADERS = List.of(
        "summary", "professional summary", "profile", "objective", "skills", "technical skills",
        "experience", "work experience", "professional experience", "employment history",
        "education", "academic background", "projects", "personal projects", "certifications",
        "certificates", "achievements", "awards", "languages"
    );
    private final ResumeRepository resumeRepository;

    public ResumeDraftService(ResumeRepository resumeRepository) { this.resumeRepository = resumeRepository; }

    @Transactional(readOnly = true)
    public CandidateProfileDraft createDraft(UUID resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
            .orElseThrow(() -> new ResourceNotFoundException("Resume not found."));
        if (!"PARSED".equals(resume.getParseStatus()) || resume.getSourceText() == null || resume.getSourceText().isBlank()) {
            throw new IllegalArgumentException("This resume has no extracted text to structure. Upload a readable PDF or DOCX first.");
        }
        String text = resume.getSourceText();
        String[] lines = text.split("\\R");
        String email = firstMatch(EMAIL, text);
        String phone = firstMatch(PHONE, text);
        String name = inferName(lines, email, phone);
        String summary = section(text, List.of("summary", "professional summary", "profile", "objective"));
        String skills = section(text, List.of("skills", "technical skills", "core competencies", "technical expertise"));
        String experience = section(text, List.of("experience", "work experience", "professional experience", "employment history"));
        String education = section(text, List.of("education", "academic background", "qualifications"));
        String projects = section(text, List.of("projects", "personal projects", "key projects"));
        String headline = inferHeadline(lines, name, email, phone);
        List<String> notes = new ArrayList<>();
        notes.add("Automatically extracted fields are suggestions. Confirm every field before saving.");
        if (name == null) notes.add("Name was not confidently identified; enter it manually.");
        if (email == null) notes.add("Email was not detected.");
        if (skills.isBlank()) notes.add("Skills section was not detected; review the original resume text.");
        if (experience.isBlank()) notes.add("Experience section was not detected; review the original resume text.");
        if (education.isBlank()) notes.add("Education section was not detected; review the original resume text.");
        return new CandidateProfileDraft(resume.getId(), resume.getCandidateProfile().getId(), name, email, phone, headline, summary, skills, experience, education, projects, notes);
    }

    private String firstMatch(Pattern pattern, String text) {
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group().trim() : null;
    }

    private String inferName(String[] lines, String email, String phone) {
        for (String raw : lines) {
            String line = raw.trim();
            if (line.isBlank() || line.length() > 70 || line.contains("@") || (phone != null && line.contains(phone))) continue;
            if (isHeader(line) || line.matches(".*\\d.*") || line.contains("|") || line.contains("http")) continue;
            String[] words = line.split("\\s+");
            if (words.length >= 2 && words.length <= 4 && line.matches("[\\p{L}][\\p{L} .'-]+")) return line;
        }
        return null;
    }

    private String inferHeadline(String[] lines, String name, String email, String phone) {
        for (String raw : lines) {
            String line = raw.trim();
            if (line.isBlank() || line.length() > 100 || line.contains("@") || line.contains("http") || isHeader(line)) continue;
            if (name != null && line.equals(name)) continue;
            if (phone != null && line.contains(phone)) continue;
            if (line.matches(".*\\d.*")) continue;
            if (line.split("\\s+").length <= 12) return line;
        }
        return null;
    }

    private boolean isHeader(String line) {
        String normalized = line.replaceAll("[:|•#]", "").trim().toLowerCase();
        return SECTION_HEADERS.contains(normalized);
    }

    private String section(String text, List<String> aliases) {
        String[] lines = text.split("\\R");
        StringBuilder result = new StringBuilder();
        boolean active = false;
        for (String raw : lines) {
            String line = raw.trim();
            String normalized = line.replaceAll("[:|•#]", "").trim().toLowerCase();
            if (SECTION_HEADERS.contains(normalized)) {
                if (active) break;
                active = aliases.contains(normalized);
                continue;
            }
            if (active && !line.isBlank()) result.append(line).append('\\n');
        }
        return result.toString().trim();
    }
}
