# AI Resume Builder

An AI-assisted resume builder that tailors resumes to job descriptions while keeping every claim grounded in the candidate's verified experience.

## MVP goals

- Upload and parse PDF/DOCX resumes
- Build an editable, structured candidate profile
- Paste a job description and extract requirements
- Analyze requirement coverage, ATS readiness, and evidence quality
- Generate a tailored resume using verified candidate facts only
- Review and edit generated versions
- Export to DOCX and PDF
- Save job descriptions and resume versions

## Proposed stack

- Frontend: React, TypeScript, Vite
- Backend: Java 21, Spring Boot
- Database: PostgreSQL with Flyway migrations
- Document parsing: Apache Tika, PDFBox, Apache POI
- AI: hosted LLM API with structured outputs
- Local development: Docker Compose

## Product principles

1. Never invent employment history, skills, dates, metrics, certifications, or education.
2. Explain scores with requirement-level evidence rather than promising a guaranteed ATS score.
3. Keep the original resume and every tailored version available to the user.
4. Treat uploaded documents and job descriptions as untrusted input.

## Initial development plan

1. Repository scaffold and local Docker environment
2. Candidate profile and resume upload/parsing
3. Job description analysis and requirement matching
4. Tailored resume generation with evidence checks
5. DOCX/PDF export and version history

Implementation details will evolve as the MVP is built.