package ru.zzzadruga.services.calculation.common;

import org.apache.ignite.services.Service;

import java.util.List;

public interface CalculationService extends Service {
    String SERVICE_NAME = "CalculationService";

    String getResult(String mathExpression);

    List<String> getHistory();

    String getStatistics();
}
