package com.example.RevierCheckliste.domain.checkliste;

import java.util.ArrayList;
import java.util.List;

public class ServiceChecklist {
    private List<ControlEntry> entries = new ArrayList<>();

    public void add(ControlEntry entry) {
        entries.add(entry);
    }

    public List<ControlEntry> getControlEntries() {
        return entries;
    }
}
