package space.devport.modular.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import space.devport.modular.DevportModular;
import space.devport.modular.commands.ModularSubCommand;
import space.devport.modular.system.struct.AbstractModule;
import space.devport.utils.commands.struct.ArgumentRange;
import space.devport.utils.commands.struct.CommandResult;
import space.devport.utils.text.message.Message;

import java.util.ArrayList;
import java.util.List;

public class ListSubCommand extends ModularSubCommand {

    public ListSubCommand(DevportModular modular) {
        super("list", modular);
    }

    @Override
    protected CommandResult perform(CommandSender sender, String label, String[] args) {

        List<AbstractModule> modules = new ArrayList<>(getPlugin().getModuleManager().getRegisteredModules().values());

        Message message = language.get("Commands.List.Header")
                .replace("%count%", modules.size());

        Message line = language.get("Commands.List.Line");
        for (AbstractModule module : modules) {
            message.append(new Message(line)
                    .replace("%module%", module.getName())
                    .replace("%state%", getState(module.isEnabled())));
        }
        message.send(sender);
        return CommandResult.SUCCESS;
    }

    private String getState(boolean bool) {
        return language.get("State." + (bool ? "Enabled" : "Disabled")).toString();
    }

    @Override
    public @NotNull String getDefaultUsage() {
        return "/%label% list";
    }

    @Override
    public @NotNull String getDefaultDescription() {
        return "List modules.";
    }

    @Override
    public @NotNull ArgumentRange getRange() {
        return new ArgumentRange(0);
    }
}