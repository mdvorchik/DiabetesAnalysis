package org.sbt.analysis.classifier;

import org.sbt.analysis.entity.FlatPatient;
import org.sbt.analysis.entity.FormalConcepts;

public interface Classifier {
    boolean classify(FlatPatient patient, FormalConcepts formalConcepts);
}
