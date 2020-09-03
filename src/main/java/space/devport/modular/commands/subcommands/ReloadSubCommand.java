package space.devport.modular.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import space.devport.modular.DevportModular;
import space.devport.modular.commands.ModularSubCommand;
import space.devport.modular.system.struct.AbstractModule;
import space.devport.utils.commands.struct.ArgumentRange;
import space.devport.utils.commands.struct.CommandResult;

public class ReloadSubCommand extends ModularSubCommand {

    public ReloadSubCommand(DevportModular modular) {
        super("reload", modular);
    }

    @Override
    protected CommandResult perform(CommandSender sender, String label, String[] args) {

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("all")) {
                getPlugin().getModuleManager().call(AbstractModule::onReload);
                language.getPrefixed("Commands.Reload.Done-All")
                        .replace("%count%", getPlugin().getModuleManager().getModules().size())
                        .send(sender);
            } else {
                AbstractModule module = getPlugin().getModuleManager().getModule(args[0]);
                if (module == null) {
                    language.getPrefixed("Commands.Invalid-Module")
                            .replace("%param%", args[0])
                            .send(sender);
                    return CommandResult.FAILURE;
                }

                module.onReload();
                language.getPrefixed("Commands.Reload.Done")
                        .replace("%module%", module.getName())
                        .send(sender);
            }
            return CommandResult.SUCCESS;
        }

        getPlugin().reload(sender);
        return CommandResult.SUCCESS;
    }

    @Override
    public @NotNull String getDefaultUsage() {
        return "/%label% reload (module)";
    }

    @Override
    public @NotNull String getDefaultDescription() {
        return "Reload the plugin, or a module.";
    }

    @Override
    public @NotNull ArgumentRange getRange() {
        return new ArgumentRange(0, 1);
    }
}