package space.devport.modular.commands;

import org.bukkit.command.CommandSender;
import space.devport.utils.commands.MainCommand;
import space.devport.utils.commands.struct.CommandResult;

public class ModularCommand extends MainCommand {

    public ModularCommand() {
        super("devportmodular");
    }

    @Override
    protected CommandResult perform(CommandSender sender, String label, String[] args) {
        return super.perform(sender, label, args);
    }

    @Override
    public String getDefaultUsage() {
        return "/%label%";
    }

    @Override
    public String getDefaultDescription() {
        return "Displays this.";
    }
}