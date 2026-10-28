package ai.destryon.resumebuilder.resume;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.jupiter.api.Test;

class ResumeParserServiceTest {
    private final ResumeParserService parser = new ResumeParserService();

    @Test
    void rejectsUnsupportedFileType() {
        assertThrows(IllegalArgumentException.class, () -> parser.extractText("resume.txt", "hello".getBytes()));
    }

    @Test
    void extractsTextFromPdf() throws Exception {
        byte[] pdf;
        try (PDDocument document = new PDDocument(); ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            PDPage page = new PDPage();
            document.addPage(page);
            try (PDPageContentStream stream = new PDPageContentStream(document, page)) {
                stream.beginText();
                stream.setFont(PDType1Font.HELVETICA, 12);
                stream.newLineAtOffset(50, 700);
                stream.showText("Candidate Resume - Java Developer");
                stream.endText();
            }
            document.save(output);
            pdf = output.toByteArray();
        }
        String text = parser.extractText("resume.pdf", pdf);
        assertTrue(text.contains("Java Developer"));
    }

    @Test
    void rejectsImageOnlyOrEmptyTextPdf() throws Exception {
        byte[] pdf;
        try (PDDocument document = new PDDocument(); ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            document.addPage(new PDPage());
            document.save(output);
            pdf = output.toByteArray();
        }
        assertThrows(IllegalArgumentException.class, () -> parser.extractText("scan.pdf", pdf));
    }
}
