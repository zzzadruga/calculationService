package ru.zzzadruga.app;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import ru.zzzadruga.services.calculation.common.CalculationService;

public class TestAppStartup {
    public static void main(String[] args) {
        Ignite ignite = Ignition.start("META-INF/config/client-node-config.xml");

        System.out.println("Connecting to cluster");

        CalculationService calculationService = ignite.services().serviceProxy(CalculationService.SERVICE_NAME, CalculationService.class, false);
        for (int i = 0; i < 10; i++) {
            System.out.println(calculationService.getSolution("" + i + " + " + 100 * i ));
        }

        System.out.println(calculationService.getHistory());
    }
}
