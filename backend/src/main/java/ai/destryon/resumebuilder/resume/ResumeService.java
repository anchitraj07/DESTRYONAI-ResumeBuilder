package ai.destryon.resumebuilder.resume;

import ai.destryon.resumebuilder.profile.CandidateProfile;
import ai.destryon.resumebuilder.profile.CandidateProfileRepository;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResumeService {
    private static final long MAX_FILE_SIZE = 10L * 1024 * 1024;
    private final ResumeRepository resumeRepository;
    private final CandidateProfileRepository profileRepository;
    private final ResumeParserService parser;

    public ResumeService(ResumeRepository resumeRepository, CandidateProfileRepository profileRepository, ResumeParserService parser) {
        this.resumeRepository = resumeRepository;
        this.profileRepository = profileRepository;
        this.parser = parser;
    }

    @Transactional
    public ResumeResponse upload(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) throw new IllegalArgumentException("Choose a non-empty resume file.");
        if (file.getSize() > MAX_FILE_SIZE) throw new IllegalArgumentException("File exceeds the 10 MB upload limit.");
        String filename = file.getOriginalFilename();
        if (filename == null || filename.isBlank()) throw new IllegalArgumentException("The uploaded file must have a filename.");
        String lower = filename.toLowerCase(Locale.ROOT);
        if (!(lower.endsWith(".pdf") || lower.endsWith(".docx"))) {
            throw new IllegalArgumentException("Unsupported file type. Upload a PDF or DOCX file.");
        }
        byte[] bytes = file.getBytes();
        if (lower.endsWith(".pdf") && !(bytes.length >= 5 && new String(bytes, 0, 5, java.nio.charset.StandardCharsets.US_ASCII).equals("%PDF-"))) {
            throw new IllegalArgumentException("The file extension says PDF, but the file signature is invalid.");
        }
        if (lower.endsWith(".docx") && !(bytes.length >= 4 && bytes[0] == 'P' && bytes[1] == 'K')) {
            throw new IllegalArgumentException("The file extension says DOCX, but the file signature is invalid.");
        }

        CandidateProfile profile = profileRepository.save(new CandidateProfile());
        Resume resume = new Resume();
        resume.setCandidateProfile(profile);
        resume.setTitle(filename.replaceFirst("(?i)\\.(pdf|docx)$", ""));
        resume.setSourceFilename(filename);
        resume.setSourceContentType(lower.endsWith(".pdf") ? MediaType.APPLICATION_PDF_VALUE : "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        resume.setSourceFile(bytes);
        resume.setFileSizeBytes(file.getSize());
        try {
            String extracted = parser.extractText(filename, bytes);
            resume.setSourceText(extracted);
            resume.setParseStatus("PARSED");
        } catch (IllegalArgumentException | IOException ex) {
            resume.setParseStatus("FAILED");
            resume.setParseError(ex.getMessage() == null ? "Could not extract text from this file." : ex.getMessage());
        }
        return ResumeResponse.from(resumeRepository.save(resume));
    }

    @Transactional(readOnly = true)
    public ResumeResponse get(UUID id) {
        return resumeRepository.findById(id).map(ResumeResponse::from)
            .orElseThrow(() -> new ResourceNotFoundException("Resume not found."));
    }
}
