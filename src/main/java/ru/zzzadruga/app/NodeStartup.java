package ru.zzzadruga.app;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

public class NodeStartup {
    public static void main(String[] args) {
        Ignite ignite = Ignition.start("META-INF/config/server-node-config.xml");
    }
}
