package org.sbt.analysis.executor;

import org.sbt.analysis.classifier.*;
import org.sbt.analysis.entity.FlatPatient;
import org.sbt.analysis.entity.FormalConcepts;
import org.sbt.analysis.entity.FormalContext;
import org.sbt.analysis.entity.Patient;
import org.sbt.analysis.util.CsvController;

import java.io.File;
import java.net.URISyntaxException;
import java.util.*;

public class Main {
    public static final Integer totalPositiveCase = 320;
    public static final Integer totalNegativeCase = 200;

    public static final Integer TPtot = 80;
    public static final Integer TNtot = 50;
    public static final Integer FPtot = 50;
    public static final Integer FNtot = 80;

    public static List<Float> TParr = new ArrayList<>();
    public static List<Float> TNarr = new ArrayList<>();
    public static List<Float> FParr = new ArrayList<>();
    public static List<Float> FNarr = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Ready to analysis by NaiveClassifier 1:");
        Classifier classifier = new NaiveClassifier();
        for (int i = 0; i < 50; i++) {
            analysis(classifier);
        }
        outData();
        clearResource();

        System.out.println("Ready to analysis by SquareClassifier 2:");
        classifier = new SquareClassifier();
        for (int i = 0; i < 50; i++) {
            analysis(classifier);
        }
        outData();
        clearResource();

        System.out.println("Ready to analysis by WithAgeClassifier 3:");
        classifier = new WithAgeClassifier();
        for (int i = 0; i < 50; i++) {
            analysis(classifier);
        }
        outData();
        clearResource();

        System.out.println("Ready to analysis by WithAgeSquareClassifier 4:");
        classifier = new WithAgeSquareClassifier();
        for (int i = 0; i < 50; i++) {
            analysis(classifier);
        }
        outData();
        clearResource();
    }

    private static void clearResource() {
        TParr = new ArrayList<>();
        TNarr = new ArrayList<>();
        FParr = new ArrayList<>();
        FNarr = new ArrayList<>();
    }

    private static void outData() {
        float TPrate = TParr.stream().reduce(Float::sum).get() / (float) TParr.size();
        float TNrate = TNarr.stream().reduce(Float::sum).get() / (float) TNarr.size();
        float FPrate = FParr.stream().reduce(Float::sum).get() / (float) FParr.size();
        float FNrate = FNarr.stream().reduce(Float::sum).get() / (float) FNarr.size();
        float falseDiscoveryRate = (FPrate * FPtot) / (FPrate * FPtot + TPrate * TPtot);
        float accuracy = (TPrate * TPtot + TNrate * TNtot) / (TPtot + TNtot);
        float precision = (TPrate * TPtot) / (FPrate * FPtot + TPrate * TPtot);
        float recall = (TPrate * TPtot) / (FNrate * FNtot + TPrate * TPtot);
        System.out.println("TP rate=" + TPrate);
        System.out.println("TN rate=" + TNrate);
        System.out.println("FP rate=" + FPrate);
        System.out.println("FN rate=" + FNrate);
        System.out.println("False Discovery Rate=" + falseDiscoveryRate);
        System.out.println("Accuracy=" + accuracy);
        System.out.println("Precision=" + precision);
        System.out.println("Recall=" + recall);
    }

    private static void analysis(Classifier classifier) {

        CsvController csvController = new CsvController();
        File dataFile = null;
        try {
            dataFile = new File(Objects.requireNonNull(Main.class.getClassLoader().getResource("diabetes_data_upload.csv")).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        //320 - positive
        //200 - negative
        List<Patient> patients = csvController.getPatientsFromCsv(dataFile);
        Collections.shuffle(patients);
        //doing analysis
        List<Patient> patientsForTraining = new ArrayList<>();
        List<Patient> patientsForTest = new ArrayList<>();
        int counterPositiveCase = 0;
        int counterNegativeCase = 0;
        int maxPositiveCaseInTraining = (int) (totalPositiveCase * 0.75);
        int maxPositiveCaseInTest = totalPositiveCase - maxPositiveCaseInTraining;
        int maxNegativeCaseInTraining = (int) (totalNegativeCase * 0.75);
        int maxNegativeCaseInTest = totalNegativeCase - maxNegativeCaseInTraining;
        for (Patient patient : patients) {
            if (patient.classification) {
                //320 * 0.75 = 240
                if (counterPositiveCase < maxPositiveCaseInTraining) {
                    counterPositiveCase++;
                    patientsForTraining.add(patient);
                } else {
                    patientsForTest.add(patient);
                }
            } else {
                //200 * 0.75 = 150
                if (counterNegativeCase < maxNegativeCaseInTraining) {
                    counterNegativeCase++;
                    patientsForTraining.add(patient);
                } else {
                    patientsForTest.add(patient);
                }
            }
        }
        List<FlatPatient> flatPatientsForTraining = new ArrayList<>();
        for (Patient patient : patientsForTraining) {
            flatPatientsForTraining.add(new FlatPatient(new ArrayList<>(Collections.singletonList(patient.age)), patient.signsToList(), patient.getIntClassWithNegative(patient.classification)));
        }
        List<FlatPatient> flatPatientsForTest = new ArrayList<>();
        for (Patient patient : patientsForTest) {
            flatPatientsForTest.add(new FlatPatient(new ArrayList<>(Collections.singletonList(patient.age)), patient.signsToList(), patient.getIntClassWithNegative(patient.classification)));
        }
        List<FlatPatient> reducedFlatPatients = new ArrayList<>();
        for (FlatPatient flatPatient : flatPatientsForTraining) {
            if (reducedFlatPatients.contains(flatPatient)) {
                FlatPatient reducedFlatPatient = reducedFlatPatients.get(reducedFlatPatients.indexOf(flatPatient));
                reducedFlatPatient.ages.add(flatPatient.ages.get(0));
                reducedFlatPatient.classification += flatPatient.classification;
            } else {
                reducedFlatPatients.add(flatPatient);
            }
        }

        FormalContext formalContext = new FormalContext(reducedFlatPatients);
        FormalConcepts formalConcepts = formalContext.toFormalConcepts();
        startAnalysis(classifier, maxPositiveCaseInTest, maxNegativeCaseInTest, flatPatientsForTest, formalConcepts);
        countAgeInfluence(patientsForTraining, reducedFlatPatients);
    }

    private static void startAnalysis(Classifier classifier, int maxPositiveCaseInTest, int maxNegativeCaseInTest, List<FlatPatient> flatPatientsForTest, FormalConcepts formalConcepts) {
        int TP = 0;
        int TN = 0;
        int FP = 0;
        int FN = 0;
        int undef = 0;

        for (FlatPatient flatPatient : flatPatientsForTest) {
                boolean isPositive = classifier.classify(flatPatient, formalConcepts);
                if (isPositive) {
                    if (flatPatient.classification >= 0) {
                        TP++;
                    } else {
                        FP++;
                    }
                } else {
                    if (flatPatient.classification < 0) {
                        TN++;
                    } else {
                        FN++;
                    }
                }
        }
        TParr.add(TP / (float) maxPositiveCaseInTest);
        TNarr.add(TN / (float) maxNegativeCaseInTest);
        FParr.add(FP / (float) maxNegativeCaseInTest);
        FNarr.add(FN / (float) maxPositiveCaseInTest);
    }

    private static void countAgeInfluence(List<Patient> patientsForTraining, List<FlatPatient> reducedFlatPatients) {
        int ageInfluence = 0;
        for (FlatPatient reducedFlatPatient : reducedFlatPatients) {
            if (reducedFlatPatient.ages.size() != Math.abs(reducedFlatPatient.classification)) {
                ageInfluence++;
            }
        }
        //System.out.printf("Age influence: %.2f%%%n", 100 * ageInfluence / (float) patientsForTraining.size());
        //Age influence: 0,26%
    }

    private static void mostFrequentSignsInPositive(List<FlatPatient> reducedFlatPatients) {
        int[] signsPos = new int[15];
        for (FlatPatient reducedFlatPatient : reducedFlatPatients) {
            if (reducedFlatPatient.classification >= 0) {
                for (int i = 0; i < signsPos.length; i++) {
                    signsPos[i] += reducedFlatPatient.signs.get(i);

                }
            }
        }
        /*
        polyuria
        polydipsia
        weakness
         */
        for (int signPos : signsPos) {
            System.out.println(signPos);
        }
    }
}
