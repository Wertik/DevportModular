package space.devport.modular.system.struct;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import space.devport.modular.DevportModular;
import space.devport.utils.configuration.Configuration;

public abstract class AbstractModule {

    @Getter
    private final DevportModular modular;

    @Getter
    private boolean enabled = false;

    public AbstractModule() {
        this(DevportModular.getInstance());
    }

    public AbstractModule(DevportModular modular) {
        this.modular = modular;
    }

    public void register() {
        modular.getModuleManager().register(getClass(), true);
    }

    public void unregister() {
        modular.getModuleManager().unregister(this);
    }

    public abstract String getName();

    public ConfigurationSection getSection() {
        return modular.getModules().section(getName());
    }

    public Configuration getConfiguration() {
        return modular.getModules();
    }

    public void enable() {
        if (this.enabled) return;

        this.enabled = true;
        this.onEnable();
        getModular().getConsoleOutput().info("Enabled module " + getName());
    }

    public void disable() {
        if (!this.enabled) return;

        this.onDisable();
        this.enabled = false;
        getModular().getConsoleOutput().info("Disabled module " + getName());
    }

    public void reload() {
        if (!this.enabled) return;

        getModular().getModules().load();
        onReload();
        getModular().getConsoleOutput().info("Reloaded module " + getName());
    }

    public void onLoad() {
    }

    public void onEnable() {
    }

    public void onReload() {
    }

    public void onDisable() {
    }
}