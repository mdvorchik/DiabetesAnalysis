package org.sbt.analysis.util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.sbt.analysis.entity.Patient;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CsvController {
    private final Predicate<String> isTrueByYes = s -> s.contains("Yes");

    public List<Patient> getPatientsFromCsv(File file) {
        List<Patient> patients = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            List<String[]> r = null;
            try {
                r = reader.readAll();
            } catch (IOException | CsvException e) {
                e.printStackTrace();
            }
            if (r != null) {
                r.remove(0);
                r.forEach(x -> {
                    Integer age = Integer.parseInt(x[0]);
                    Boolean isMan = x[1].contains("Male");
                    Boolean polyuria = isTrueByYes.test(x[2]);
                    Boolean polydipsia = isTrueByYes.test(x[3]);
                    Boolean suddenWeightLoss = isTrueByYes.test(x[4]);
                    Boolean weakness = isTrueByYes.test(x[5]);
                    Boolean polyphagia = isTrueByYes.test(x[6]);
                    Boolean genitalThrush = isTrueByYes.test(x[7]);
                    Boolean visualBlurring = isTrueByYes.test(x[8]);
                    Boolean itching = isTrueByYes.test(x[9]);
                    Boolean irritability = isTrueByYes.test(x[10]);
                    Boolean delayedHealing = isTrueByYes.test(x[11]);
                    Boolean partialParesis = isTrueByYes.test(x[12]);
                    Boolean muscleStiffness = isTrueByYes.test(x[13]);
                    Boolean alopecia = isTrueByYes.test(x[14]);
                    Boolean obesity = isTrueByYes.test(x[15]);
                    Boolean classification = x[16].contains("Positive");
                    patients.add(new Patient(
                            age,
                            isMan,
                            polyuria,
                            polydipsia,
                            suddenWeightLoss,
                            weakness,
                            polyphagia,
                            genitalThrush,
                            visualBlurring,
                            itching,
                            irritability,
                            delayedHealing,
                            partialParesis,
                            muscleStiffness,
                            alopecia,
                            obesity,
                            classification));
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return patients;
    }
}
