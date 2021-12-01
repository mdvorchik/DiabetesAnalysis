package org.sbt.analysis.classifier;

import org.sbt.analysis.entity.FlatPatient;
import org.sbt.analysis.entity.FormalConcepts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaiveClassifier implements Classifier {
    @Override
    public boolean classify(FlatPatient patient, FormalConcepts formalConcepts) {
        float naiveClassifier = 0f;
        Map<List<Integer>, List<FlatPatient>> source = new HashMap<>(formalConcepts.formalConcepts);
        Map<List<Integer>, Integer> sourceClassification = new HashMap<>();
        source.forEach((key, value) -> {
            Integer classification = value.stream()
                    .map(v -> v.classification)
                    .reduce(Integer::sum)
                    .get();
            sourceClassification.put(key, classification);
        });
        Map<Integer, Integer> distanceToClassification = new HashMap<>();
        sourceClassification.forEach((signs, classification) -> {
            Integer distance = getDistance(patient.signs, signs);
            distanceToClassification.put(distance, classification);
        });

        for (Map.Entry<Integer, Integer> entry : distanceToClassification.entrySet()) {
            Integer dist = entry.getKey();
            Integer clazz = entry.getValue();
            naiveClassifier += clazz / (float) (dist + 1);
        }

        return naiveClassifier > 0;
    }

    private Integer getDistance(List<Integer> signs, List<Integer> anotherSigns) {
        int distance = 0;
        for (int i = 0; i < signs.size(); i++) {
            if (!signs.get(i).equals(anotherSigns.get(i))) {
                distance++;
            }
        }
        return distance;
    }
}
