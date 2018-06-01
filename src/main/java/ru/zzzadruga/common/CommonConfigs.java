package ru.zzzadruga.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.ignite.services.ServiceConfiguration;
import ru.zzzadruga.services.calculation.CalculationServiceImpl;
import ru.zzzadruga.services.calculation.common.CalculationService;
import ru.zzzadruga.services.mathOperations.AddService;
import ru.zzzadruga.services.mathOperations.DivideService;
import ru.zzzadruga.services.mathOperations.MultiplyService;
import ru.zzzadruga.services.mathOperations.SubtractService;

public class CommonConfigs {
    public static final ServiceConfiguration CALCULATION_SERVICE_CONFIG = new ServiceConfiguration().
        setName(CalculationService.SERVICE_NAME).setService(new CalculationServiceImpl())
        .setTotalCount(1).setMaxPerNodeCount(1);
    private static final Map<String, ServiceConfiguration> mathServicesCfg = new HashMap<>();

    static {
        mathServicesCfg.put(AddService.SERVICE_NAME, new ServiceConfiguration().
            setName(AddService.SERVICE_NAME).setService(new AddService())
            .setTotalCount(10).setMaxPerNodeCount(3));
        mathServicesCfg.put(SubtractService.SERVICE_NAME, new ServiceConfiguration().
            setName(SubtractService.SERVICE_NAME).setService(new SubtractService())
            .setTotalCount(10).setMaxPerNodeCount(3));
        mathServicesCfg.put(MultiplyService.SERVICE_NAME, new ServiceConfiguration()
            .setName(MultiplyService.SERVICE_NAME).setService(new MultiplyService())
            .setTotalCount(10).setMaxPerNodeCount(3));
        mathServicesCfg.put(DivideService.SERVICE_NAME, new ServiceConfiguration()
            .setName(DivideService.SERVICE_NAME).setService(new DivideService())
            .setTotalCount(10).setMaxPerNodeCount(3));
    }

    public static Map<String, ServiceConfiguration> MATH_SERVICES_CONFIG() {
        return Collections.unmodifiableMap(mathServicesCfg);
    }
}
