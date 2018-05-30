package ru.zzzadruga.services.calculation.common;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MathExpression {
    private String mathExpression;
    private LocalDateTime additionTime;
    private LocalDateTime decisionTime;
    private boolean isValid;
    private BigDecimal result;

    public MathExpression(String mathExpression, LocalDateTime additionTime) {
        this.mathExpression = mathExpression;
        this.additionTime = additionTime;
    }

    public String getMathExpression() {
        return mathExpression;
    }

    public void setMathExpression(String mathExpression) {
        this.mathExpression = mathExpression;
    }

    public LocalDateTime getAdditionTime() {
        return additionTime;
    }

    public void setAdditionTime(LocalDateTime additionTime) {
        this.additionTime = additionTime;
    }

    public LocalDateTime getDecisionTime() {
        return decisionTime;
    }

    public void setDecisionTime(LocalDateTime decisionTime) {
        this.decisionTime = decisionTime;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "MathExpression{" +
                "mathExpression='" + mathExpression + '\'' +
                ", additionTime=" + additionTime +
                ", decisionTime=" + decisionTime +
                ", isValid=" + isValid +
                ", result=" + result +
                '}';
    }
}
