package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table
public class ResultPrediction extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private ResultPredictionValues resultPrediction;

    public ResultPrediction() {
    }

    public ResultPredictionValues getResultPrediction() {
        return resultPrediction;
    }

    public void setResultPrediction(ResultPredictionValues resultPrediction) {
        this.resultPrediction = resultPrediction;
    }
}
