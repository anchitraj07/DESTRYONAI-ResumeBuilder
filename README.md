# Destryon AI Resume Builder

An AI-assisted resume builder that tailors resumes to job descriptions while keeping every claim grounded in the candidate's verified experience.

## Current status

**Milestone 2 — Resume ingestion and profile review:** PDF/DOCX upload, text extraction, original-file storage, heuristic candidate profile draft, editable profile form, and profile persistence.

Profile draft extraction uses deterministic rules and section headings, not an LLM. Results are suggestions only and must be reviewed by the user. Complex layouts and nonstandard section headings may not be detected accurately. Scanned/image-only PDFs require OCR, which is not included yet. Authentication is not implemented, so run this locally only and do not expose it to the public internet.

## Stack

- Frontend: React, TypeScript, Vite
- Backend: Java 21, Spring Boot
- Database: PostgreSQL 16 with Flyway migrations
- PDF parsing: Apache PDFBox
- DOCX parsing: Apache POI
- Local development: Docker Compose
- Planned AI: hosted LLM API with structured outputs

## Run locally with Docker

1. Install Docker Desktop and ensure its engine is running.
2. From the repository root, run:

   ```bash
   docker compose up --build
   ```

3. Open the frontend at `http://localhost:5173`.
4. Check the API at `http://localhost:8080/api/health`.

To stop the stack, run `docker compose down`. To also remove the local database volume, run `docker compose down -v` (this deletes local database data).

The default database password is for local development only. Set `POSTGRES_PASSWORD` in your environment before using the stack beyond a local prototype.

## API endpoints

- `POST /api/resumes` — multipart upload using field `file`; accepts PDF or DOCX up to 10 MB.
- `GET /api/resumes/{id}` — retrieve resume metadata and extracted text.
- `POST /api/resumes/{id}/profile-draft` — create a heuristic draft of candidate fields and resume sections.
- `GET /api/profiles/{id}` — retrieve the candidate profile.
- `PUT /api/profiles/{id}` — save reviewed profile fields and editable section text.

## Tests

From the `backend` directory, with Java 21 and Maven installed:

```bash
mvn test
```

The GitHub Actions workflow also runs backend tests and the frontend build on pushes and pull requests.

## MVP scope

- [x] Upload and parse text-based PDF/DOCX resumes
- [x] Save original file and extracted text
- [x] Create a heuristic candidate profile draft from resume sections
- [x] Review and edit profile fields before saving
- [ ] Normalize skills, experience, education, and projects into individual structured records
- [ ] Paste a job description and extract requirements
- [ ] Analyze requirement coverage and evidence quality
- [ ] Generate a tailored resume using verified candidate facts only
- [ ] Edit and save generated versions
- [ ] Export DOCX and PDF

## Product principles

1. Never invent employment history, skills, dates, metrics, certifications, or education.
2. Explain scores with requirement-level evidence rather than promising a guaranteed ATS score.
3. Keep the original resume and every tailored version available to the user.
4. Treat uploaded documents and job descriptions as untrusted input.
