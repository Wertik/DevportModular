package space.devport.modular.commands;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;
import space.devport.modular.DevportModular;
import space.devport.utils.commands.SubCommand;
import space.devport.utils.commands.struct.ArgumentRange;

public abstract class ModularSubCommand extends SubCommand {

    @Getter
    private final DevportModular plugin;

    public ModularSubCommand(String name, DevportModular plugin) {
        super(name);
        setPermissions();
        this.plugin = plugin;
    }

    @Override
    public @Nullable
    abstract String getDefaultUsage();

    @Override
    public @Nullable
    abstract String getDefaultDescription();

    @Override
    public @Nullable
    abstract ArgumentRange getRange();
}