import { useState } from 'react';
import { ArrowUpRight, FileText, Sparkles, Target, Upload, CheckCircle2, BriefcaseBusiness, ShieldCheck } from 'lucide-react';

const steps = [
  { number: '01', title: 'Add your resume', detail: 'Upload a PDF or DOCX, or start with a blank profile.' },
  { number: '02', title: 'Paste the job description', detail: 'We’ll identify required skills, experience, and keywords.' },
  { number: '03', title: 'Review your match', detail: 'See where your resume has evidence and where it needs work.' },
];

export default function App() {
  const [jobDescription, setJobDescription] = useState('');
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [notice, setNotice] = useState('');
  const [uploading, setUploading] = useState(false);
  const [uploadResult, setUploadResult] = useState<{id:string;candidateProfileId:string;parseStatus:string;parseError:string|null;extractedText:string|null}|null>(null);

  async function handleAnalyze() {
    if (!selectedFile) {
      setNotice('Choose a PDF or DOCX resume first.');
      return;
    }
    if (selectedFile.size > 10 * 1024 * 1024) {
      setNotice('This file is larger than 10 MB. Choose a smaller file.');
      return;
    }
    setUploading(true);
    setNotice('');
    setUploadResult(null);
    try {
      const formData = new FormData();
      formData.append('file', selectedFile);
      const response = await fetch('http://localhost:8080/api/resumes', { method: 'POST', body: formData });
      const body = await response.json();
      if (!response.ok) throw new Error(body.message ?? 'Resume upload failed.');
      setUploadResult(body);
      setNotice(body.parseStatus === 'PARSED'
        ? 'Resume uploaded and text extracted successfully. Review the extracted text below.'
        : `Resume was saved, but text extraction needs attention: ${body.parseError ?? 'unknown parsing issue'}`);
    } catch (error) {
      setNotice(error instanceof Error ? error.message : 'Could not connect to the backend. Make sure Docker is running.');
    } finally {
      setUploading(false);
    }
  }
  return (
    <main className="app-shell">
      <nav className="topbar">
        <a className="brand" href="#top" aria-label="Destryon Resume Builder home"><span className="brand-mark"><Sparkles size={18} /></span><span>DESTRYON<span className="brand-muted"> / RESUME</span></span></a>
        <div className="nav-right"><span className="status-dot" /> MVP workspace <button className="text-button" onClick={() => document.getElementById('builder')?.scrollIntoView({ behavior: 'smooth' })}>Get started <ArrowUpRight size={15} /></button></div>
      </nav>

      <section className="hero" id="top">
        <div className="hero-copy">
          <div className="eyebrow"><span className="eyebrow-line" /> CAREER TOOLS, BUILT WITH EVIDENCE</div>
          <h1>Your experience.<br /><span>Your next opportunity.</span></h1>
          <p className="hero-description">Create a job-specific resume that makes your real skills easier to find, understand, and evaluate. No invented achievements. No mystery scores.</p>
          <div className="hero-actions"><button className="primary-button" onClick={() => document.getElementById('builder')?.scrollIntoView({ behavior: 'smooth' })}>Build my resume <ArrowUpRight size={17} /></button><div className="trust-note"><ShieldCheck size={16} /> Your facts stay your facts</div></div>
          <div className="hero-stats"><div><strong>01</strong><span>One source of truth</span></div><div><strong>JD → CV</strong><span>Targeted, not generic</span></div><div><strong>Evidence first</strong><span>Transparent matching</span></div></div>
        </div>
        <div className="preview-wrap" aria-label="Illustrative resume analysis preview">
          <div className="preview-glow" />
          <div className="resume-card"><div className="resume-top"><div><span className="tiny-label">RESUME ANALYSIS</span><h3>Backend Engineer</h3><p>Target role · Product engineering</p></div><div className="score-ring"><span>78</span><small>/100</small></div></div><div className="score-caption"><span>JOB COMPATIBILITY</span><span className="score-positive">Illustrative preview</span></div><div className="score-track"><span /></div><div className="match-list"><div><CheckCircle2 size={16} /><span><b>Spring Boot</b><small>Supported by work experience</small></span><em>Strong</em></div><div><CheckCircle2 size={16} /><span><b>REST APIs</b><small>Found in project details</small></span><em>Strong</em></div><div className="match-missing"><Target size={16} /><span><b>Cloud deployment</b><small>Add evidence if you have it</small></span><em>Review</em></div></div><div className="preview-foot"><ShieldCheck size={15} /> Score is a guide, not an ATS guarantee</div></div>
          <div className="floating-tag"><span className="tag-icon"><FileText size={15} /></span><span><b>Tailored version</b><small>Ready for your review</small></span></div>
        </div>
      </section>

      <section className="builder-section" id="builder">
        <div className="section-heading"><div><div className="eyebrow"><span className="eyebrow-line" /> START WITH THE BASICS</div><h2>Build your next version.</h2></div><p>Bring your current resume and the role you want. We’ll help you identify the gap before rewriting anything.</p></div>
        <div className="builder-grid">
          <div className="input-panel"><div className="panel-heading"><span className="panel-number">01</span><div><h3>Your current resume</h3><p>PDF or Word document</p></div></div><label className="upload-zone" htmlFor="resume-file"><input id="resume-file" type="file" accept=".pdf,.doc,.docx" onChange={(event) => { setSelectedFile(event.target.files?.[0] ?? null); setNotice(''); }} /><span className="upload-icon"><Upload size={21} /></span><b>{selectedFile ? selectedFile.name : 'Choose a resume to upload'}</b><small>{selectedFile ? `${(selectedFile.size / 1024).toFixed(0)} KB · Selected locally` : 'PDF, DOC, DOCX · Up to 10 MB'}</small><span className="choose-link">Browse files</span></label><p className="privacy-note"><ShieldCheck size={14} /> The selected file is sent to your local backend when you prepare analysis.</p></div>
          <div className="input-panel"><div className="panel-heading"><span className="panel-number">02</span><div><h3>Target job description</h3><p>Paste the full role details</p></div></div><label className="sr-only" htmlFor="job-description">Job description</label><textarea id="job-description" value={jobDescription} onChange={(event) => { setJobDescription(event.target.value); setNotice(''); }} placeholder="Paste the job description here… Include responsibilities, required skills, and qualifications." /><div className="textarea-meta"><span>{jobDescription.trim() ? jobDescription.trim().split(/\s+/).length : 0} words</span><span>More detail = better analysis</span></div></div>
        </div>
        <div className="builder-footer"><div className="step-hint"><BriefcaseBusiness size={17} /><span><b>Uploads the resume to the local API and extracts readable text</small></span></div><button className="primary-button" onClick={handleAnalyze} disabled={uploading}>{uploading ? 'Uploading and parsing…' : 'Upload and parse resume'} <ArrowUpRight size={17} /></button></div>
        {notice && <div className="notice" role="status">{notice}</div>}
        {uploadResult && <div className="parsed-result"><div className="parsed-result-head"><div><h3>Resume ingestion result</h3><p>Resume ID: {uploadResult.id}</p><p>Candidate profile ID: {uploadResult.candidateProfileId}</p></div><span className={uploadResult.parseStatus === 'PARSED' ? 'parse-pill success' : 'parse-pill failed'}>{uploadResult.parseStatus}</span></div>{uploadResult.extractedText && <><label htmlFor="extracted-text">Extracted text (read-only preview)</label><textarea id="extracted-text" readOnly value={uploadResult.extractedText} /></>}</div>}
      </section>

      <section className="process-section"><div className="section-heading"><div><div className="eyebrow"><span className="eyebrow-line" /> A CLEARER WORKFLOW</div><h2>Three steps. One honest resume.</h2></div><p>The product is designed to improve relevance while preserving accuracy and your control over every claim.</p></div><div className="process-grid">{steps.map((step) => <article className="process-card" key={step.number}><span>{step.number}</span><h3>{step.title}</h3><p>{step.detail}</p></article>)}</div></section>
      <footer><a className="brand footer-brand" href="#top"><span className="brand-mark"><Sparkles size={16} /></span><span>DESTRYON<span className="brand-muted"> / RESUME</span></span></a><span>Built to support your next move — not make up your past.</span><span>Prototype · v0.1</span></footer>
    </main>
  );
}
