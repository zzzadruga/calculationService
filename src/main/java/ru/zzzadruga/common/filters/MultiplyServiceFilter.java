package ru.zzzadruga.common.filters;

import java.util.HashSet;
import java.util.Set;
import org.apache.ignite.Ignite;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.lang.IgnitePredicate;
import org.apache.ignite.resources.IgniteInstanceResource;
import ru.zzzadruga.services.mathOperations.MultiplyService;

public class MultiplyServiceFilter implements IgnitePredicate<ClusterNode> {
    Set<ClusterNode> usedNodes = new HashSet<>();
    @IgniteInstanceResource
    private Ignite ignite;

    @Override public boolean apply(ClusterNode node) {
        return Balancer.getResult(usedNodes, node, ignite, MultiplyService.SERVICE_NAME);
    }
}
