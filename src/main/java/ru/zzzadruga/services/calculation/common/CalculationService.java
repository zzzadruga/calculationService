package ru.zzzadruga.services.calculation.common;

import java.util.List;
import org.apache.ignite.services.Service;

import java.util.Map;
import ru.zzzadruga.services.mathOperations.common.MathOperationService;

public interface CalculationService extends Service {
    String SERVICE_NAME = "CalculationService";

    String getResult(String mathExpression);

    List<String> getHistory();

    String getStatistics();

    Map<String, MathOperationService> getMap();

    long getAmount();
}
