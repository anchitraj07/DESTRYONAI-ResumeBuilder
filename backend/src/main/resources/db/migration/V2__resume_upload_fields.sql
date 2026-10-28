ALTER TABLE resumes
    ADD COLUMN source_file BYTEA,
    ADD COLUMN file_size_bytes BIGINT,
    ADD COLUMN parse_status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    ADD COLUMN parse_error TEXT;

CREATE TABLE candidate_skills (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    candidate_profile_id UUID NOT NULL REFERENCES candidate_profiles(id) ON DELETE CASCADE,
    skill_name VARCHAR(120) NOT NULL,
    source_evidence TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_candidate_skills_profile ON candidate_skills(candidate_profile_id);

CREATE TABLE work_experiences (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    candidate_profile_id UUID NOT NULL REFERENCES candidate_profiles(id) ON DELETE CASCADE,
    employer VARCHAR(200),
    job_title VARCHAR(200),
    location VARCHAR(160),
    start_date DATE,
    end_date DATE,
    "current_role" BOOLEAN NOT NULL DEFAULT FALSE,
    description TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_work_experiences_profile ON work_experiences(candidate_profile_id);

CREATE TABLE education_entries (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    candidate_profile_id UUID NOT NULL REFERENCES candidate_profiles(id) ON DELETE CASCADE,
    institution VARCHAR(240),
    qualification VARCHAR(200),
    field_of_study VARCHAR(200),
    start_date DATE,
    end_date DATE,
    grade VARCHAR(80),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_education_entries_profile ON education_entries(candidate_profile_id);
