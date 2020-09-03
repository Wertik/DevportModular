package space.devport.modular;

import space.devport.utils.text.language.LanguageDefaults;

public class ModularLanguage extends LanguageDefaults {

    @Override
    public void setDefaults() {
        addDefault("Commands.Invalid-Module", "&cModule &f%param% &cis invalid.");

        addDefault("Commands.Reload.Done-All", "&7Reloaded all (&f%count%&7) modules.");
        addDefault("Commands.Reload.Done", "&7Reloaded module &f%module%");

        addDefault("Commands.Unload.Error", "&cCould not load module &f%param%");
        addDefault("Commands.Unload.Done", "&7Unloaded module &f%module%");

        addDefault("Commands.Load.Error", "&cCould not load module &f%param%");
        addDefault("Commands.Load.Done", "&7Loaded module &f%module%&7, current state: &f%state%");

        addDefault("Commands.List.Header", "&8&m    &3 Modules &8&m    ");
        addDefault("Commands.List.Line", "&8 - &7%module% &7(%state%&7)");

        addDefault("Commands.Enable.Done", "&7Enabled module &f%module%&7, current state: &r%state%");

        addDefault("Commands.Disable.Done", "&7Disabled module &f%module%&7, current state: &f%state%");

        addDefault("State.Enabled", "&aenabled");
        addDefault("State.Disabled", "&cdisabled");
    }
}
