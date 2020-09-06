package space.devport.modular;

import lombok.Getter;
import org.bukkit.Bukkit;
import space.devport.modular.commands.ModularCommand;
import space.devport.modular.commands.subcommands.*;
import space.devport.modular.system.ModuleManager;
import space.devport.modular.system.struct.AbstractModule;
import space.devport.utils.ConsoleOutput;
import space.devport.utils.DevportPlugin;
import space.devport.utils.UsageFlag;
import space.devport.utils.configuration.Configuration;

public class DevportModular extends DevportPlugin {

    @Getter
    private ModuleManager moduleManager;

    @Getter
    private Configuration modules;

    @Override
    public void onLoad() {

        this.consoleOutput = ConsoleOutput.getInstance();

        this.modules = new Configuration(this, "modules");

        // Load all modules
        this.moduleManager = new ModuleManager(this);

        this.moduleManager.load();

        // Call on load
        this.moduleManager.call(AbstractModule::onLoad);
    }

    @Override
    public void onPluginEnable() {

        new ModularLanguage();

        addMainCommand(new ModularCommand())
                .addSubCommand(new ReloadSubCommand(this))
                .addSubCommand(new LoadSubCommand(this))
                .addSubCommand(new UnloadSubCommand(this))
                .addSubCommand(new ListSubCommand(this))
                .addSubCommand(new DisableSubCommand(this))
                .addSubCommand(new EnableSubCommand(this));

        Bukkit.getScheduler().runTaskAsynchronously(this, () -> this.moduleManager.call(AbstractModule::enable));
    }

    @Override
    public void onPluginDisable() {
        this.moduleManager.call(AbstractModule::disable);
    }

    @Override
    public void onReload() {
        this.moduleManager.call(AbstractModule::reload);
    }

    @Override
    public UsageFlag[] usageFlags() {
        return new UsageFlag[]{UsageFlag.CONFIGURATION, UsageFlag.LANGUAGE, UsageFlag.COMMANDS};
    }

    public static DevportModular getInstance() {
        return getPlugin(DevportModular.class);
    }
}