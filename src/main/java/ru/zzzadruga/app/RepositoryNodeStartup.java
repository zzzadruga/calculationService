package ru.zzzadruga.app;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

public class RepositoryNodeStartup {
    public static void main(String[] args) {
        Ignite ignite = Ignition.start("META-INF/config/repository-node-config.xml");
    }
}
