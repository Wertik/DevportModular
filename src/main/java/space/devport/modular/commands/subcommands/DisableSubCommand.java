package space.devport.modular.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import space.devport.modular.DevportModular;
import space.devport.modular.commands.ModularSubCommand;
import space.devport.modular.system.struct.AbstractModule;
import space.devport.utils.commands.struct.ArgumentRange;
import space.devport.utils.commands.struct.CommandResult;

import java.util.ArrayList;
import java.util.List;

public class DisableSubCommand extends ModularSubCommand {

    public DisableSubCommand(DevportModular modular) {
        super("disable", modular);
    }

    @Override
    protected CommandResult perform(CommandSender sender, String label, String[] args) {

        AbstractModule module = getPlugin().getModuleManager().getModule(args[0]);

        if (module == null) {
            language.getPrefixed("Commands.Invalid-Module")
                    .replace("%param%", args[0])
                    .send(sender);
            return CommandResult.FAILURE;
        }

        module.disable();
        language.getPrefixed("Commands.Disable.Done")
                .replace("%module%", module.getName())
                .replace("%state%", getState(module.isEnabled()))
                .send(sender);
        return CommandResult.SUCCESS;
    }

    @Override
    public List<String> requestTabComplete(CommandSender sender, String[] args) {
        return args.length == 1 ? new ArrayList<>(getPlugin().getModuleManager().getModules()) : new ArrayList<>();
    }

    private String getState(boolean bool) {
        return language.get("State." + (bool ? "Enabled" : "Disabled")).toString();
    }

    @Override
    public @NotNull String getDefaultUsage() {
        return "/%label% disable <name>";
    }

    @Override
    public @NotNull String getDefaultDescription() {
        return "Disable a module.";
    }

    @Override
    public @NotNull ArgumentRange getRange() {
        return new ArgumentRange(1);
    }
}