package ai.destryon.resumebuilder.resume;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ResumeRepository extends JpaRepository<Resume, UUID> {}
