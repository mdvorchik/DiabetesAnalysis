package org.sbt.analysis.entity;

import java.util.List;
import java.util.Objects;

public class FlatPatient {
    public final List<Integer> ages;
    //must have 15 size
    public final List<Integer> signs;
    public Integer classification;


    public FlatPatient(List<Integer> ages, List<Integer> signs, Integer classification) {
        this.ages = ages;
        this.signs = signs;
        this.classification = classification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlatPatient that = (FlatPatient) o;
        return Objects.equals(signs, that.signs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(signs);
    }

    @Override
    public String toString() {
        return "FlatPatient{" +
                "ages=" + ages +
                ", signs=" + signs +
                ", classification=" + classification +
                '}';
    }
}
