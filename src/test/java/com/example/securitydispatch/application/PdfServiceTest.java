package com.example.securitydispatch.application;

import com.example.securitydispatch.domain.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PdfServiceTest {
    private final Zone zone = new Zone(1L, "Zone 1");
    private final Driver driver = new Driver(1L, "Max", "Mustermann");

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
            1L, shift, standardConfiguration, LocalDateTime.now(), List.of());

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
                List.of(new Warning("Friday rule active")));

        byte[] result = pdfService.generateChecklistPdf(checklistWithWarnings);

        assertThat(result).isNotNull();
        assertThat(result.length).isGreaterThan(0);
    }
}