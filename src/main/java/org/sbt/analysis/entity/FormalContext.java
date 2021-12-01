package org.sbt.analysis.entity;

import java.util.*;
import java.util.stream.Collectors;

public class FormalContext {
    private final int signSize = 15;
    private final List<FlatPatient> formalContext;

    public FormalContext(List<FlatPatient> formalContext) {
        this.formalContext = formalContext;
    }

    public FormalConcepts toFormalConcepts() {
        Map<List<Integer>, List<FlatPatient>> formalConcepts = new HashMap<>();
        //first sign
        for (int i = 0; i < signSize; i++) {
            Integer[] signArr = new Integer[signSize];
            Arrays.fill(signArr, 0);
            signArr[i] = 1;
            List<Integer> signsForSearch = Arrays.stream(signArr).collect(Collectors.toList());
            List<FlatPatient> formalConceptsFromSearch = getPatientsBySigns(signsForSearch);
            List<Integer> signsFromSearch = getSignsByPatients(formalConceptsFromSearch);
            if (signsFromSearch == null) {
                continue;
            }
            do {
                if (signsFromSearch.equals(signsForSearch)) {
                    formalConcepts.put(signsFromSearch, formalConceptsFromSearch);
                } else {
                    signsForSearch = signsFromSearch;
                    formalConceptsFromSearch = getPatientsBySigns(signsForSearch);
                    signsFromSearch = getSignsByPatients(formalConceptsFromSearch);
                }
            } while (!signsFromSearch.equals(signsForSearch));
        }
        //first object
        for (int i = 0; i < formalContext.size(); i++) {
            List<Integer> signsForSearch = formalContext.get(i).signs;
            List<FlatPatient> formalConceptsFromSearch = getPatientsBySigns(signsForSearch);
            List<Integer> signsFromSearch = getSignsByPatients(formalConceptsFromSearch);
            if (signsFromSearch == null) {
                continue;
            }
            do {
                if (signsFromSearch.equals(signsForSearch)) {
                    formalConcepts.put(signsFromSearch, formalConceptsFromSearch);
                } else {
                    signsForSearch = signsFromSearch;
                    formalConceptsFromSearch = getPatientsBySigns(signsForSearch);
                    signsFromSearch = getSignsByPatients(formalConceptsFromSearch);
                }
            } while (!signsFromSearch.equals(signsForSearch));
        }
        return new FormalConcepts(formalConcepts);
    }

    public List<FlatPatient> getPatientsBySigns(List<Integer> signs) {
        List<FlatPatient> flatPatients = new ArrayList<>();
        for (FlatPatient patient : formalContext) {
            if (isPartOf(signs, patient.signs)) {
                flatPatients.add(patient);
            }
        }
        return flatPatients;
    }

    private boolean isPartOf(List<Integer> signs, List<Integer> anotherSigns) {
        for (int i = 0; i < anotherSigns.size(); i++) {
            if (signs.get(i) > anotherSigns.get(i)) {
                return false;
            }
        }
        return true;
    }

    public List<Integer> getSignsByPatients(List<FlatPatient> flatPatients) {
        for (FlatPatient patient : flatPatients) {
            boolean isPartOF = true;
            for (FlatPatient anotherPatient : flatPatients) {
                if (!patient.isPartOf(anotherPatient)) {
                    isPartOF = false;
                }
            }
            if (isPartOF) {
                return patient.signs;
            }
        }
        return null;
    }
}
