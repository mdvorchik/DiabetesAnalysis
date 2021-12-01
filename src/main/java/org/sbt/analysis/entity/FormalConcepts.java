package org.sbt.analysis.entity;

import java.util.List;
import java.util.Map;

public class FormalConcepts {
    public final Map<List<Integer>, List<FlatPatient>> formalConcepts;

    public FormalConcepts(Map<List<Integer>, List<FlatPatient>> formalConcepts) {
        this.formalConcepts = formalConcepts;
    }
}
