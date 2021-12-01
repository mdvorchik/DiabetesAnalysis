package org.sbt.analysis.entity;

import java.util.ArrayList;
import java.util.List;

public class FormalContext {
    private final List<FlatPatient> formalContext = new ArrayList<>();

    public void addObject(FlatPatient flatPatient) {
        formalContext.add(flatPatient);
    }

    public List<FlatPatient> toFormalConcepts () {
        List<FlatPatient> formalConcepts = new ArrayList<>();


        return formalConcepts;
    }

    public List<FlatPatient> getPatientsBySigns (FlatPatient flatPatient) {
        List<FlatPatient> flatPatients = new ArrayList<>();
        for (FlatPatient patient : formalContext) {
            if (flatPatient.isPartOf(patient)) {
                flatPatients.add(patient);
            }
        }
        return flatPatients;
    }

    public FlatPatient getSignsByPatients (List<FlatPatient> flatPatients) {
        for (FlatPatient patient : flatPatients) {
            boolean isPartOF = true;
            for (FlatPatient anotherPatient : flatPatients) {
                if (!patient.isPartOf(anotherPatient)) {
                    isPartOF = false;
                }
            }
            if (isPartOF) {
                return patient;
            }
        }
        return null;
    }
}
