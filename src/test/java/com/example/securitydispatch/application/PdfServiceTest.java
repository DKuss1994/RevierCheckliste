package com.example.securitydispatch.application;

import com.example.securitydispatch.domain.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;

public class PdfServiceTest {
    private final Zone zone = new Zone(1L, "Zone 1");
    private final Driver driver = new Driver(1L, "Max", "Mustermann");
    private final Address address = new Address("Street 1", "City", "33333");

    private final Shift shift = new Shift(

            1L,
            driver,
            zone,
            LocalDate.now(),
            LocalTime.of(22, 0),
            LocalTime.of(6, 0)

    );
    private final StandardConfiguration standardConfiguration = new StandardConfiguration.Builder()
            .inspectionCount(2)
            .closingTime(LocalTime.of(23, 0))
            .openingTime(LocalTime.of(5, 0))
            .build();
    private final Checklist checklist = new Checklist(
            1L, shift, standardConfiguration, LocalDateTime.now(), List.of(), List.of());

    private final PdfService pdfService = new PdfService();

    @Test
    void shouldGeneratePdf() {
        byte[] result = pdfService.generateChecklistPdf(checklist);

        assertThat(result).isNotNull();
        assertThat(result.length).isGreaterThan(0);
        assertThat(new String(result, 0, 4)).isEqualTo("%PDF");
    }

    @Test
    void shouldGeneratePdfWithWarnings() {
        Checklist checklistWithWarnings = new Checklist(
                2L, shift, standardConfiguration, LocalDateTime.now(),
                List.of(new Warning("Friday rule active")), List.of());

        byte[] result = pdfService.generateChecklistPdf(checklistWithWarnings);

        assertThat(result).isNotNull();
        assertThat(result.length).isGreaterThan(0);
    }

    @Test
    void pdfShouldHaveOneLineHeaderAndPerObjectInspectionTable() throws Exception {
        StandardConfiguration configA = new StandardConfiguration.Builder()
                .inspectionCount(2).build();
        StandardConfiguration configB = new StandardConfiguration.Builder()
                .inspectionCount(1).build();
        SecurityObject object1 = new SecurityObject(1L, "Object A", zone, address, configA);
        SecurityObject object2 = new SecurityObject(2L, "Object B", zone, address, configB);

        ChecklistEntry entry1 = new ChecklistEntry(object1, object1.getStandardConfiguration());
        ChecklistEntry entry2 = new ChecklistEntry(object2, object2.getStandardConfiguration());

        StandardConfiguration globalConfig = new StandardConfiguration.Builder().build();
        Checklist testChecklist = new Checklist(1L, shift, globalConfig, LocalDateTime.now(), List.of(),
                List.of(entry1, entry2));
        byte[] pdfBytes = pdfService.generateChecklistPdf(testChecklist);

        try (PdfReader reader = new PdfReader(new ByteArrayInputStream(pdfBytes))) {
            PdfTextExtractor extractor = new PdfTextExtractor(reader);
            String pageContent = extractor.getTextFromPage(1);

            // Header in einer Zeile
            assertThat(pageContent).containsPattern(
                    "Driver: Max Mustermann \\| Zone: Zone 1 \\| Date: \\d{4}-\\d{2}-\\d{2} \\| Shift: 22:00 → 06:00( \\(Night Shift\\))?"
            );

            // Objektnamen
            assertThat(pageContent).contains("Object A", "Object B");

            // Kästchenanzahl
            assertThat(pageContent).containsPattern("Object A.*□.*□");
            assertThat(pageContent).containsPattern("Object B.*□");

            assertThat(pageContent).contains("Time Window");
            assertThat(pageContent).contains("22:00 → 06:00"); // oder die tatsächlichen Schichtzeiten
        }

    }
    @Test
    void pdfShouldContainClosingTable() throws Exception {
        // Objekt mit closingTime
        StandardConfiguration config = new StandardConfiguration.Builder()
                .closingTime(LocalTime.of(23, 0))
                .build();
        SecurityObject object = new SecurityObject(1L, "Object A", zone, address, config);
        ChecklistEntry entry = new ChecklistEntry(object, config);
        Checklist testChecklist = new Checklist(1L, shift, new StandardConfiguration.Builder().build(),
                LocalDateTime.now(), List.of(), List.of(entry));
        byte[] pdfBytes = pdfService.generateChecklistPdf(testChecklist);
        try (PdfReader reader = new PdfReader(new ByteArrayInputStream(pdfBytes))) {
            PdfTextExtractor extractor = new PdfTextExtractor(reader);
            String text = extractor.getTextFromPage(1);
            assertThat(text).contains("CLOSING");
            assertThat(text).contains("23:00");
        }
    }
    @Test
    void pdfShouldContainOpeningTable() throws Exception {
        // Objekt mit openingTime
        StandardConfiguration config = new StandardConfiguration.Builder()
                .openingTime(LocalTime.of(23, 0))
                .build();
        SecurityObject object = new SecurityObject(1L, "Object A", zone, address, config);
        ChecklistEntry entry = new ChecklistEntry(object, config);
        Checklist testChecklist = new Checklist(1L, shift, new StandardConfiguration.Builder().build(),
                LocalDateTime.now(), List.of(), List.of(entry));
        byte[] pdfBytes = pdfService.generateChecklistPdf(testChecklist);
        try (PdfReader reader = new PdfReader(new ByteArrayInputStream(pdfBytes))) {
            PdfTextExtractor extractor = new PdfTextExtractor(reader);
            String text = extractor.getTextFromPage(1);
            assertThat(text).contains("OPENING");
            assertThat(text).contains("23:00");
        }
    }

}