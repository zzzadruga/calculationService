package ru.zzzadruga.app;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

public class SubtractServiceNodeStartup {
    public static void main(String[] args) {
        Ignite ignite = Ignition.start("META-INF/config/subtract-service-node-config.xml");
    }
}
