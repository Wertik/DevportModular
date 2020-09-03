package space.devport.modular.system;

import lombok.Getter;
import space.devport.modular.DevportModular;
import space.devport.modular.FileUtil;
import space.devport.modular.system.struct.AbstractModule;
import space.devport.modular.system.struct.MethodCall;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ModuleManager {

    private final DevportModular plugin;

    @Getter
    private final File moduleFolder;

    private final Map<String, AbstractModule> registeredModules = new HashMap<>();

    public ModuleManager(DevportModular plugin) {
        this.plugin = plugin;

        this.moduleFolder = new File(plugin.getDataFolder(), "modules/");
        this.moduleFolder.mkdirs();
    }

    /**
     * Load all modules.
     */
    public void load() {
        for (Class<? extends AbstractModule> moduleClass : findModules()) {
            register(moduleClass);
        }
    }

    public boolean register(Class<? extends AbstractModule> moduleClass, boolean... enable) {

        AbstractModule module = createInstance(moduleClass);

        if (this.registeredModules.containsKey(module.getName())) {
            plugin.getConsoleOutput().err("Could not register " + module.getName() + ", already registered.");
            return false;
        }

        this.registeredModules.put(module.getName(), module);
        plugin.getConsoleOutput().info("Registered module " + module.getName());

        if (enable.length > 0 && enable[0] && !module.isEnabled()) {
            module.onLoad();
            module.enable();
        }
        return true;
    }

    public void unregister(AbstractModule module) {

        if (module == null || !this.registeredModules.containsKey(module.getName()))
            return;

        module.disable();
        this.registeredModules.remove(module.getName());
        plugin.getConsoleOutput().info("Unregistered module " + module.getName());
    }

    public void unregister(String name) {
        unregister(getModule(name));
    }

    /**
     * Load module by name.
     */
    public boolean load(String name) {
        File moduleFile = new File(moduleFolder, name + (name.endsWith(".jar") ? "" : ".jar"));

        if (!moduleFile.exists()) {
            plugin.getConsoleOutput().err("Could not load module " + name + ", doesn't exist.");
            return false;
        }

        Class<? extends AbstractModule> moduleClass;
        try {
            moduleClass = FileUtil.findClass(moduleFile, AbstractModule.class);
        } catch (IOException | ClassNotFoundException e) {
            plugin.getConsoleOutput().err("Could not load module " + name + ", " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        return register(moduleClass, true);
    }

    public AbstractModule getModule(String name) {
        return this.registeredModules.get(name);
    }

    public void call(MethodCall<AbstractModule> call) {
        for (AbstractModule module : this.registeredModules.values()) {
            call.call(module);
        }
    }

    /**
     * Find modules in modules folder.
     */
    public List<Class<? extends AbstractModule>> findModules() {
        List<Class<? extends AbstractModule>> classes = new ArrayList<>();

        for (File file : moduleFolder.listFiles()) {

            if (!file.getName().endsWith(".jar")) continue;

            try {
                Class<? extends AbstractModule> clazz = FileUtil.findClass(file, AbstractModule.class);

                if (clazz == null) {
                    continue;
                }

                classes.add(clazz);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return classes;
    }

    public AbstractModule createInstance(Class<? extends AbstractModule> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, AbstractModule> getRegisteredModules() {
        return Collections.unmodifiableMap(this.registeredModules);
    }

    public Set<String> getModules() {
        return getRegisteredModules().keySet();
    }
}