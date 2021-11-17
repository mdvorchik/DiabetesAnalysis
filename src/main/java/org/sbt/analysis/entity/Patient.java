package org.sbt.analysis.entity;

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
