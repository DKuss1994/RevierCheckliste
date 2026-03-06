package com.example.RevierCheckliste.application.service;
import com.example.RevierCheckliste.domain.checkliste.ServiceChecklist;
import com.example.RevierCheckliste.domain.service.PatrolService;
import com.example.RevierCheckliste.domain.service.ServiceCheckliste;
import com.example.RevierCheckliste.domain.site.Site;
import com.example.RevierCheckliste.domain.site.StandardConfiguration;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChecklistGeneratorServiceTest {
    @Test
    void generatesControlsFromStandardConfiguration(){
        StandardConfiguration config = new StandardConfiguration(2);
        Site site = new Site("Bank",config);
        PatrolService service= new PatrolService();
        ChecklistGeneratorService generator = new ChecklistGeneratorService();
        ServiceChecklist checkliste = generator.generate(service, List.of(site));
        assertEquals(2,checkliste.getControlEntries().size());
    }
    @Test
    void generatesControlsFromTwoObjectStandardConfiguration(){
        StandardConfiguration config = new StandardConfiguration(2);
        StandardConfiguration config2 = new StandardConfiguration(1);
        Site site = new Site("Bank",config);
        Site site2 = new Site("Büro",config2);
        PatrolService service= new PatrolService();
        ChecklistGeneratorService generator = new ChecklistGeneratorService();
        ServiceChecklist checkliste = generator.generate(service, List.of(site,site2));
        assertEquals(3,checkliste.getControlEntries().size());
    }
}
