package ru.zzzadruga.common.filters;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.ignite.Ignite;
import org.apache.ignite.cluster.ClusterNode;
import ru.zzzadruga.services.calculation.common.CalculationService;
import ru.zzzadruga.services.mathOperations.common.MathOperationService;

public class Balancer {
    public static boolean getResult(Set<ClusterNode> usedNodes, ClusterNode node, Ignite ignite, String serviceName) {
        Collection<ClusterNode> allNodes = ignite.cluster().topology(ignite.cluster().topologyVersion()).stream()
            .filter(v -> !v.isClient()).collect(Collectors.toList());
        if (usedNodes.isEmpty()) {
            usedNodes.add(node);
            return true;
        }
        else {
            if (allNodes.containsAll(usedNodes)) {
                CalculationService calculationServiceProxy = ignite.services()
                    .serviceProxy(CalculationService.SERVICE_NAME, CalculationService.class, false);
                long amountOperations = calculationServiceProxy.getAmount();
                System.out.println(amountOperations);
                if (amountOperations != 0) {
                    long max = calculationServiceProxy.getMap().entrySet().stream()
                        .max(Map.Entry.comparingByValue(Comparator.comparingLong(MathOperationService::getCount)))
                        .get().getValue().getCount();
                    int servicesPerNode = (int)Math.ceil(amountOperations / (double)max);
                    long operationCount = calculationServiceProxy.getMap().get(serviceName).getCount();
                    int amountNodes = allNodes.size();
                    int operationNodes = usedNodes.size();
                    int needNodes = Math.round(operationCount/(float)max * amountNodes);
                    if ((operationNodes < needNodes)){
                        usedNodes.add(node);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
