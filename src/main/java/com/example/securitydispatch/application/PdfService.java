package com.example.securitydispatch.application;

import com.example.securitydispatch.domain.Checklist;
import com.example.securitydispatch.domain.ChecklistEntry;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    public byte[] generateChecklistPdf(Checklist checklist) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, outputStream);
            document.open();

            addHeader(document, checklist);
            addInspectionsTable(document, checklist);
            addClosingTable(document, checklist);
            addOpeningTable(document, checklist);
            addWarnings(document, checklist);

            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }

    private void addHeader(Document document, Checklist checklist) throws Exception {
        var shift = checklist.getShift();
        String header = String.format(
                "Driver: %s %s | Zone: %s | Date: %s | Shift: %s → %s%s",
                shift.getDriver().getFirstName(),
                shift.getDriver().getLastName(),
                shift.getZone().getName(),
                shift.getDeploymentDate(),
                shift.getStartTime(),
                shift.getEndTime(),
                shift.isNightShift() ? " (Night Shift)" : ""
        );
        document.add(new Paragraph(header));
        document.add(Chunk.NEWLINE);
    }

    private void addInspectionsTable(Document document, Checklist checklist) throws Exception {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3f, 2f, 2f});
        table.addCell("Security Object");
        table.addCell("Time Window");
        table.addCell("Inspections");

        for (ChecklistEntry entry : checklist.getEntries()) {
            var obj = entry.getSecurityObject();
            var config = entry.getResolvedConfiguration();

            int count = config.getInspectionCount().orElse(0);
            table.addCell(obj.getName());

            String timeWindow = checklist.getShift().getStartTime() + " - " + checklist.getShift().getEndTime();
            table.addCell(timeWindow);

            Paragraph boxes = new Paragraph();
            for (int i = 0; i < count; i++) {
                boxes.add(new Chunk("□ "));
            }
            PdfPCell cell = new PdfPCell(boxes);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
        }
        document.add(table);
        document.add(Chunk.NEWLINE);
    }

    private void addClosingTable(Document document, Checklist checklist) throws Exception {
        var entriesWithClosing = checklist.getEntries().stream()
                .filter(e -> e.getResolvedConfiguration().getClosingTime().isPresent())
                .toList();
        if (entriesWithClosing.isEmpty()) return;

        document.add(new Paragraph("CLOSING"));
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.addCell("Security Object");
        table.addCell("Closing Time");
        table.addCell("Checked");

        for (ChecklistEntry entry : entriesWithClosing) {
            table.addCell(entry.getSecurityObject().getName());
            table.addCell(entry.getResolvedConfiguration().getClosingTime().get().toString());
            table.addCell("□ ___________");
        }
        document.add(table);
        document.add(Chunk.NEWLINE);
    }

    private void addOpeningTable(Document document, Checklist checklist) throws Exception {
        var entriesWithOpening = checklist.getEntries().stream()
                .filter(e -> e.getResolvedConfiguration().getOpeningTime().isPresent())
                .toList();
        if (entriesWithOpening.isEmpty()) return;

        document.add(new Paragraph("OPENING"));
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.addCell("Security Object");
        table.addCell("Opening Time");
        table.addCell("Checked");

        for (ChecklistEntry entry : entriesWithOpening) {
            table.addCell(entry.getSecurityObject().getName());
            String timeStr = entry.getResolvedConfiguration().getOpeningTime().get().toString();
            if (checklist.getShift().isNightShift()) {
                timeStr = checklist.getShift().getDeploymentDate().plusDays(1) + " " + timeStr;
            }
            table.addCell(timeStr);
            table.addCell("□ ___________");
        }
        document.add(table);
        document.add(Chunk.NEWLINE);
    }

    private void addWarnings(Document document, Checklist checklist) throws Exception {
        if (!checklist.getWarnings().isEmpty()) {
            document.add(new Paragraph("WARNINGS"));
            for (var warning : checklist.getWarnings()) {
                document.add(new Paragraph("⚠ " + warning.getMessage()));
            }
        }
    }
}