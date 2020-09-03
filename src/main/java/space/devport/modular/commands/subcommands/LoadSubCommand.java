package space.devport.modular.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import space.devport.modular.DevportModular;
import space.devport.modular.commands.ModularSubCommand;
import space.devport.modular.system.struct.AbstractModule;
import space.devport.utils.commands.struct.ArgumentRange;
import space.devport.utils.commands.struct.CommandResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LoadSubCommand extends ModularSubCommand {

    public LoadSubCommand(DevportModular modular) {
        super("load", modular);
    }

    @Override
    protected CommandResult perform(CommandSender sender, String label, String[] args) {

        AbstractModule module = getPlugin().getModuleManager().load(args[0]);

        if (module == null) {
            language.getPrefixed("Commands.Load.Error")
                    .replace("%param%", args[0])
                    .send(sender);
            return CommandResult.FAILURE;
        } else {
            language.getPrefixed("Commands.Load.Done")
                    .replace("%module%", module.getName())
                    .replace("%state%", getState(module.isEnabled()))
                    .send(sender);
        }
        return CommandResult.SUCCESS;
    }

    @Override
    public List<String> requestTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return Arrays.stream(getPlugin().getModuleManager().getModuleFolder().listFiles())
                    .map(f -> f.getName().replace(".jar", "").split("-")[0])
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
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