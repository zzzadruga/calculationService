package ru.zzzadruga.app;

import java.util.Collection;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.services.ServiceDescriptor;
import ru.zzzadruga.services.calculation.common.CalculationService;

public class TestAppStartup {
    public static void main(String[] args) throws InterruptedException {
        Ignite ignite = Ignition.start("META-INF/config/client-node-config.xml");
        System.out.println("Connecting to cluster");
        CalculationService calculationService = ignite.services()
            .serviceProxy(CalculationService.SERVICE_NAME, CalculationService.class, false);
        printServiceDescriptor(ignite);
        System.out.println(calculationService.getStatistics());
        System.out.println(calculationService.getResult("(2 + 2 * 2) / 3"));
        System.out.println(calculationService.getStatistics());
        printServiceDescriptor(ignite);

    }

    private static void printServiceDescriptor(Ignite ignite) {
        Collection<ServiceDescriptor> collection = ignite.services().serviceDescriptors();
        for (ServiceDescriptor serviceDescriptor : collection) {
            System.out.println("cacheName: " + serviceDescriptor.cacheName() + ";\n" +
                "name: " + serviceDescriptor.name() + ";\n" +
                "maxPerNodeCount: " + serviceDescriptor.maxPerNodeCount() + ";\n" +
                "originNodeId: " + serviceDescriptor.originNodeId() + ";\n" +
                "totalCount: " + serviceDescriptor.totalCount() + ";\n"
            );
        }
    }
}
