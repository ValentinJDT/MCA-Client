package fr.mrcoq;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

    private Thread thread;
    private Client client;
    private static Plugin instance;
    private FileConfiguration configuration;
    private PluginManager pluginManager;

    @Override
    public void onEnable() {
        instance = this;
        pluginManager = getServer().getPluginManager();
        configuration = getConfig();
        saveDefaultConfig();

        thread = new Thread(() -> {
            client = new Client();
            client.start();
            System.out.println("Started");
        });

        thread.start();

        pluginManager.registerEvents(new Events(), this);

    }

    @Override
    public void onDisable() {
        this.client.stop();
        this.thread.stop();
    }

    public static Plugin getInstance() {
        return instance;
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    public Client getClient() {
        return this.client;
    }

}
