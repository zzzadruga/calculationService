package ru.zzzadruga.services.calculation.common;

import org.apache.ignite.services.Service;

public interface CalculationService extends Service {
    String SERVICE_NAME = "CalculationService";

    String getSolution(String mathExpression);
}
