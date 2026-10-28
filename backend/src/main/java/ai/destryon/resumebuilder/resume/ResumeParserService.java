package ai.destryon.resumebuilder.resume;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Locale;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;

@Service
public class ResumeParserService {
    public String extractText(String filename, byte[] bytes) throws IOException {
        String lower = filename.toLowerCase(Locale.ROOT);
        String text;
        if (lower.endsWith(".pdf")) {
            try (var document = Loader.loadPDF(bytes)) {
                text = new PDFTextStripper().getText(document);
            }
        } else if (lower.endsWith(".docx")) {
            try (var input = new ByteArrayInputStream(bytes); var document = new XWPFDocument(input)) {
                StringBuilder result = new StringBuilder();
                for (XWPFParagraph paragraph : document.getParagraphs()) {
                    if (!paragraph.getText().isBlank()) result.append(paragraph.getText()).append('\n');
                }
                for (XWPFTable table : document.getTables()) {
                    for (XWPFTableRow row : table.getRows()) {
                        result.append(String.join(" | ", row.getTableCells().stream().map(cell -> cell.getText().trim()).toList())).append('\n');
                    }
                }
                text = result.toString();
            }
        } else {
            throw new IllegalArgumentException("Only PDF and DOCX files are supported.");
        }
        String normalized = text.replace("\u0000", "").replaceAll("[\\t\\x0B\\f\\r]+", " ").replaceAll(" *\\n *", "\n").trim();
        if (normalized.isBlank()) {
            throw new IllegalArgumentException("No readable text was found. The file may be scanned or image-only; OCR is not supported yet.");
        }
        return normalized;
    }
}
