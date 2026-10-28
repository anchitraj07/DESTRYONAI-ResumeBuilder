# Destryon AI Resume Builder

An AI-assisted resume builder that tailors resumes to job descriptions while keeping every claim grounded in the candidate's verified experience.

## Current status

**Milestone 1 — Project foundation:** repository structure, initial React UI, Spring Boot API, PostgreSQL schema, Flyway migration, and Docker Compose.

The current browser UI is a prototype. File selection stays in the browser; document parsing, AI analysis, persistence APIs, and exports are not implemented yet.

## Stack

- Frontend: React, TypeScript, Vite
- Backend: Java 21, Spring Boot
- Database: PostgreSQL 16 with Flyway migrations
- Local development: Docker Compose
- Planned document parsing: Apache Tika, PDFBox, Apache POI
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

## MVP scope

- Upload and parse PDF/DOCX resumes
- Build an editable, structured candidate profile
- Paste a job description and extract requirements
- Analyze requirement coverage, ATS readiness, and evidence quality
- Generate a tailored resume using verified candidate facts only
- Review and edit generated versions
- Export to DOCX and PDF
- Save job descriptions and resume versions

## Product principles

1. Never invent employment history, skills, dates, metrics, certifications, or education.
2. Explain scores with requirement-level evidence rather than promising a guaranteed ATS score.
3. Keep the original resume and every tailored version available to the user.
4. Treat uploaded documents and job descriptions as untrusted input.
