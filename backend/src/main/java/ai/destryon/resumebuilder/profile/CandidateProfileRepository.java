package ai.destryon.resumebuilder.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CandidateProfileRepository extends JpaRepository<CandidateProfile, UUID> {}
