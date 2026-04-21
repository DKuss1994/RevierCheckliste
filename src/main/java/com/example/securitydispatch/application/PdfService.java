package com.example.securitydispatch.application;

import com.example.securitydispatch.domain.Checklist;

import com.example.securitydispatch.domain.ChecklistEntry;
import com.example.securitydispatch.domain.Shift;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

import com.example.securitydispatch.domain.Checklist;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
@Service
public class PdfService {

    private static final float[] OBJECT_TABLE_COLUMNS = {3, 2, 1}; // Objekt | Geplant | Eingetragen

    public byte[] generateChecklistPdf(Checklist checklist) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, outputStream);
            document.open();

            addHeader(document, checklist);
            addInspections(document, checklist);
            addClosing(document, checklist);
            addOpening(document, checklist);
            addWarnings(document, checklist);

            document.close();
            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);

        }
    }

    private void addClosing(Document document, Checklist checklist)throws Exception {
        checklist.getConfiguration().getClosingTime().ifPresent(time -> {
            try {
                document.add(new Paragraph("CLOSING"));
                document.add(new Paragraph("Closing time: " + time + "  □ ___________"));
                document.add(Chunk.NEWLINE);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void addOpening(Document document, Checklist checklist) throws Exception {
        checklist.getConfiguration().getOpeningTime().ifPresent(time -> {
            try {
                String label = checklist.getShift().isNightShift()
                        ? "OPENING (next morning)"
                        : "OPENING";
                document.add(new Paragraph(label));
                document.add(new Paragraph("Opening time: " + time + "  □ ___________"));
                document.add(Chunk.NEWLINE);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void addWarnings(Document document, Checklist checklist) throws Exception {
        if (!checklist.getWarnings().isEmpty()) {
            document.add(new Paragraph("WARNINGS"));
            for (var warning : checklist.getWarnings()) {
                document.add(new Paragraph("⚠ " + warning.getMessage()));
            }
        }

    }

    private void addInspections(Document document, Checklist checklist)throws Exception  {
        if (checklist.getEntries().isEmpty()) {
            document.add(new Paragraph("No security objects assigned."));
            return;
        }

        PdfPTable table = new PdfPTable(2); // erstmal 2 Spalten: Objekt | Kästchen
        table.setWidthPercentage(100);
        table.addCell("Security Object");
        table.addCell("Inspections");

        for (ChecklistEntry entry : checklist.getEntries()) {
            table.addCell(entry.getSecurityObject().getName());
            int count = entry.getResolvedConfiguration().getInspectionCount().orElse(0);
            StringBuilder boxes = new StringBuilder();
            for (int i = 0; i < count; i++) boxes.append("□ ");
            table.addCell(boxes.toString());
        }
        document.add(table);
        document.add(Chunk.NEWLINE);
    }

    private void addHeader(Document document, Checklist checklist)throws Exception  {
        Shift shift = checklist.getShift();
        String header = String.format("Driver: %s %s | Zone: %s | Date: %s | Shift: %s → %s%s",
                shift.getDriver().getFirstName(),
                shift.getDriver().getLastName(),
                shift.getZone().getName(),
                shift.getDeploymentDate(),
                shift.getStartTime(),
                shift.getEndTime(),
                shift.isNightShift() ? " (Night Shift)" : "");
        document.add(new Paragraph(header));
        document.add(Chunk.NEWLINE);

    }
}