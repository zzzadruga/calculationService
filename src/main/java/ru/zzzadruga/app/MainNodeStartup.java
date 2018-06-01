package ru.zzzadruga.app;

import java.util.Map;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.services.ServiceConfiguration;
import ru.zzzadruga.common.CommonConfigs;

public class MainNodeStartup {
    public static void main(String[] args) {
        Ignite ignite = Ignition.start("META-INF/config/server-node-config.xml");
        ignite.services().deploy(CommonConfigs.CALCULATION_SERVICE_CONFIG);
        for (Map.Entry<String, ServiceConfiguration> entry : CommonConfigs.MATH_SERVICES_CONFIG().entrySet()) {
            ignite.services().deploy(entry.getValue());
        }

    }
}
