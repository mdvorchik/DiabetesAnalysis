package org.sbt.analysis.entity;

import java.util.ArrayList;
import java.util.List;

public class FormalContext {
    private final List<FlatPatient> formalContext = new ArrayList<>();

    public void addObject(FlatPatient flatPatient) {
        formalContext.add(flatPatient);
    }
}
