package ru.zzzadruga.common.filters;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.ignite.Ignite;
import org.apache.ignite.cluster.ClusterNode;
import ru.zzzadruga.services.calculation.common.CalculationService;

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
                    long addCount = calculationServiceProxy.getMap().get(serviceName).getCount();
                    long amountNodes = allNodes.size();
                    long addNodes = usedNodes.size();
                    long operationsPerc = (long)(((float)addCount / (float)amountOperations) * 100);
                    long nodesPerc = (long)(((float)addNodes / (float)amountNodes) * 100);
                    if (operationsPerc > nodesPerc) {
                        usedNodes.add(node);
                        return true;
                    }
                }
                else {
                    return false;
                }
            }
        }
        return false;
    }
}
