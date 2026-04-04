package com.example.securitydispatch.application;

import com.example.securitydispatch.domain.Checklist;

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
        checklist.getConfiguration().getInspectionCount().ifPresent(count -> {
            try {
                document.add(new Paragraph("INSPECTIONS"));
                for (int i = 1; i <= count; i++) {
                    document.add(new Paragraph("Inspection " + i + ":  □ ___________"));
                }
                document.add(Chunk.NEWLINE);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void addHeader(Document document, Checklist checklist)throws Exception  {
        document.add(new Paragraph("SecurityDispatch — Patrol Checklist"));
        document.add(new Paragraph("Driver: " + checklist.getShift().getDriver().getFirstName()
                + " " + checklist.getShift().getDriver().getLastName()));
        document.add(new Paragraph("Zone: " + checklist.getShift().getZone().getName()));
        document.add(new Paragraph("Date: " + checklist.getShift().getDeploymentDate()));
        document.add(new Paragraph("Shift: " + checklist.getShift().getStartTime()
                + " → " + checklist.getShift().getEndTime()));
        document.add(Chunk.NEWLINE);
    }
}