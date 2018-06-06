package ru.zzzadruga.app;

import java.util.Map;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.services.ServiceConfiguration;
import ru.zzzadruga.common.CommonConfigs;
import ru.zzzadruga.common.filters.AddServiceFilter;
import ru.zzzadruga.common.filters.DivideServiceFilter;
import ru.zzzadruga.common.filters.MultiplyServiceFilter;
import ru.zzzadruga.common.filters.SubtractServiceFilter;
import ru.zzzadruga.services.mathOperations.AddService;
import ru.zzzadruga.services.mathOperations.DivideService;
import ru.zzzadruga.services.mathOperations.MultiplyService;
import ru.zzzadruga.services.mathOperations.SubtractService;

public class MainNodeStartup {
    public static void main(String[] args) throws InterruptedException {
        Ignite ignite = Ignition.start("META-INF/config/server-node-config.xml");
        ClusterGroup group = ignite.cluster();
        Map<String, ServiceConfiguration> maths = CommonConfigs.MATH_SERVICES_CONFIG();
        ignite.services(group).deploy(CommonConfigs.CALCULATION_SERVICE_CONFIG);
        ignite.services(group.forPredicate(new AddServiceFilter())).deploy(maths.get(AddService.SERVICE_NAME));
        ignite.services(group.forPredicate(new SubtractServiceFilter()))
            .deploy(maths.get(SubtractService.SERVICE_NAME));
        ignite.services(group.forPredicate(new MultiplyServiceFilter()))
            .deploy(maths.get(MultiplyService.SERVICE_NAME));
        ignite.services(group.forPredicate(new DivideServiceFilter())).deploy(maths.get(DivideService.SERVICE_NAME));

    }
}
