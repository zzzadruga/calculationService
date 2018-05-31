package ru.zzzadruga.app;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

public class DivideServiceNodeStartup {
    public static void main(String[] args) {
        Ignite ignite = Ignition.start("META-INF/config/divide-service-node-config.xml");
    }
}
