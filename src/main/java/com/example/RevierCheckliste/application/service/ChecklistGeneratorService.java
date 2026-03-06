package com.example.RevierCheckliste.application.service;

import com.example.RevierCheckliste.domain.checkliste.ControlEntry;
import com.example.RevierCheckliste.domain.checkliste.ServiceChecklist;
import com.example.RevierCheckliste.domain.service.PatrolService;
import com.example.RevierCheckliste.domain.site.Site;

import java.util.List;

public class ChecklistGeneratorService {
    public ServiceChecklist generate(PatrolService service, List<Site> sites){
        ServiceChecklist checklist = new ServiceChecklist();
        for (Site site : sites) {
            int count = site.getConfiguration().getControlCount();
            for (int i = 0; i < count; i++) {
                checklist.add(new ControlEntry());
            }

        }
        return checklist;
    }
}
