package ru.zzzadruga.app;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import ru.zzzadruga.services.calculation.common.CalculationService;

public class TestAppStartup {
    public static void main(String[] args) {
        Ignite ignite = Ignition.start("META-INF/config/client-node-config.xml");

        System.out.println("Connecting to cluster");

        CalculationService calculationService = ignite.services().serviceProxy(CalculationService.SERVICE_NAME, CalculationService.class, false);
        System.out.println(calculationService.getStatistics());
        System.out.println(calculationService.getResult("(2 + 2 * 2) / 3"));
        System.out.println(calculationService.getStatistics());
        System.out.println(calculationService.getResult("0/1"));
        System.out.println(calculationService.getStatistics());
        System.out.println(calculationService.getResult("1/0"));
        System.out.println(calculationService.getStatistics());
        System.out.println(calculationService.getResult("1/error"));
        System.out.println(calculationService.getStatistics());
        System.out.println(calculationService.getHistory());
    }
}
