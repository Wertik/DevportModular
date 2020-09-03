package space.devport.modular.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import space.devport.modular.DevportModular;
import space.devport.modular.commands.ModularSubCommand;
import space.devport.modular.system.struct.AbstractModule;
import space.devport.utils.commands.struct.ArgumentRange;
import space.devport.utils.commands.struct.CommandResult;

public class LoadSubCommand extends ModularSubCommand {

    public LoadSubCommand(DevportModular modular) {
        super("load", modular);
    }

    @Override
    protected CommandResult perform(CommandSender sender, String label, String[] args) {

        if (!getPlugin().getModuleManager().load(args[0])) {
            language.getPrefixed("Commands.Load.Error")
                    .replace("%param%", args[0])
                    .send(sender);
            return CommandResult.FAILURE;
        } else {
            AbstractModule module = getPlugin().getModuleManager().getModule(args[0]);
            language.getPrefixed("Commands.Load.Done")
                    .replace("%module%", module.getName())
                    .replace("%state%", getState(module.isEnabled()))
                    .send(sender);
        }
        return CommandResult.SUCCESS;
    }

    private String getState(boolean bool) {
        return language.get("State." + (bool ? "Enabled" : "Disabled")).toString();
    }

    @Override
    public @NotNull String getDefaultUsage() {
        return "/%label% load <name>";
    }

    @Override
    public @NotNull String getDefaultDescription() {
        return "Load a new module.";
    }

    @Override
    public @NotNull ArgumentRange getRange() {
        return new ArgumentRange(1);
    }
}