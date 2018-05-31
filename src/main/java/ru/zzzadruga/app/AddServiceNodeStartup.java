package ru.zzzadruga.app;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

public class AddServiceNodeStartup {
    public static void main(String[] args) {
        Ignite ignite = Ignition.start("META-INF/config/add-service-node-config.xml");
    }
}
