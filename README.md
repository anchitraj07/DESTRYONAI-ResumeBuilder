# Destryon AI Resume Builder

An AI-assisted resume builder that tailors resumes to job descriptions while keeping every claim grounded in the candidate's verified experience.

## Current status

**Milestone 2 — Resume ingestion foundation implemented:** PDF/DOCX upload API, text extraction, original-file storage in PostgreSQL, candidate profile read/update endpoints, validation, and parser tests.

The upload flow currently extracts text only. It does not yet automatically identify and populate work history, skills, or education from the extracted text. Scanned/image-only PDFs require OCR, which is not included yet. Authentication is not implemented, so run this locally only and do not expose it to the public internet.

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

- `POST /api/resumes` — multipart upload using form field `file`; accepts PDF or DOCX up to 10 MB.
- `GET /api/resumes/{id}` — get metadata and extracted text for a resume.
- `GET /api/profiles/{id}` — get the associated candidate profile.
- `PUT /api/profiles/{id}` — update candidate profile fields.

Upload responses include `parseStatus` (`PARSED` or `FAILED`), an explanatory `parseError` when extraction fails, the extracted text, and IDs for the resume and candidate profile. A failed parse can still be saved for review; the original file is retained.

## Test the backend

From the `backend` directory, with Java 21 and Maven installed:

```bash
mvn test
```

Or run the entire stack with Docker Compose as described above.

## MVP scope

- [x] Upload and parse text-based PDF/DOCX resumes
- [x] Save original file and extracted text
- [x] Candidate profile read/update API
- [ ] Automatically structure experience, skills, education, projects, and certifications
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
