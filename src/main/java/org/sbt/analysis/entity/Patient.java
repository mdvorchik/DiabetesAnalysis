package org.sbt.analysis.entity;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    public final Integer age;
    public final Boolean isMan;
    public final Boolean polyuria;
    public final Boolean polydipsia;
    public final Boolean suddenWeightLoss;
    public final Boolean weakness;
    public final Boolean polyphagia;
    public final Boolean genitalThrush;
    public final Boolean visualBlurring;
    public final Boolean itching;
    public final Boolean irritability;
    public final Boolean delayedHealing;
    public final Boolean partialParesis;
    public final Boolean muscleStiffness;
    public final Boolean alopecia;
    public final Boolean obesity;
    public final Boolean classification;

    public Patient(Integer age,
                   Boolean isMan,
                   Boolean polyuria,
                   Boolean polydipsia,
                   Boolean suddenWeightLoss,
                   Boolean weakness,
                   Boolean polyphagia,
                   Boolean genitalThrush,
                   Boolean visualBlurring,
                   Boolean itching,
                   Boolean irritability,
                   Boolean delayedHealing,
                   Boolean partialParesis,
                   Boolean muscleStiffness,
                   Boolean alopecia,
                   Boolean obesity,
                   Boolean classification) {
        this.age = age;
        this.isMan = isMan;
        this.polyuria = polyuria;
        this.polydipsia = polydipsia;
        this.suddenWeightLoss = suddenWeightLoss;
        this.weakness = weakness;
        this.polyphagia = polyphagia;
        this.genitalThrush = genitalThrush;
        this.visualBlurring = visualBlurring;
        this.itching = itching;
        this.irritability = irritability;
        this.delayedHealing = delayedHealing;
        this.partialParesis = partialParesis;
        this.muscleStiffness = muscleStiffness;
        this.alopecia = alopecia;
        this.obesity = obesity;
        this.classification = classification;
    }

    public List<Integer> signsToList() {
        List<Integer> signs = new ArrayList<>();
        signs.add(getIntClass(isMan));
        signs.add(getIntClass(polyuria));
        signs.add(getIntClass(polydipsia));
        signs.add(getIntClass(suddenWeightLoss));
        signs.add(getIntClass(weakness));
        signs.add(getIntClass(polyphagia));
        signs.add(getIntClass(genitalThrush));
        signs.add(getIntClass(visualBlurring));
        signs.add(getIntClass(itching));
        signs.add(getIntClass(irritability));
        signs.add(getIntClass(delayedHealing));
        signs.add(getIntClass(partialParesis));
        signs.add(getIntClass(muscleStiffness));
        signs.add(getIntClass(alopecia));
        signs.add(getIntClass(obesity));
        return signs;
    }

    public Integer getIntClass(Boolean sign) {
        return sign ? 1 : 0;
    }

    public Integer getIntClassWithNegative(Boolean sign) {
        return sign ? 1 : -1;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "age=" + age +
                ", isMan=" + isMan +
                ", polyuria=" + polyuria +
                ", polydipsia=" + polydipsia +
                ", suddenWeightLoss=" + suddenWeightLoss +
                ", weakness=" + weakness +
                ", polyphagia=" + polyphagia +
                ", genitalThrush=" + genitalThrush +
                ", visualBlurring=" + visualBlurring +
                ", itching=" + itching +
                ", irritability=" + irritability +
                ", delayedHealing=" + delayedHealing +
                ", partialParesis=" + partialParesis +
                ", muscleStiffness=" + muscleStiffness +
                ", alopecia=" + alopecia +
                ", obesity=" + obesity +
                ", classification=" + classification +
                '}';
    }
}
