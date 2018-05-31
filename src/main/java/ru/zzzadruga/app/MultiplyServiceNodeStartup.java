package ru.zzzadruga.app;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

public class MultiplyServiceNodeStartup {
    public static void main(String[] args) {
        Ignite ignite = Ignition.start("META-INF/config/multiply-service-node-config.xml");
    }
}
