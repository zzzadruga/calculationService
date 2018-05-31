package ru.zzzadruga.services.mathOperations.common;

import org.apache.ignite.services.Service;

import java.math.BigDecimal;

public interface MathOperationService extends Service {
    BigDecimal calculate(BigDecimal operand1, BigDecimal operand2);
    long getCount();
}
