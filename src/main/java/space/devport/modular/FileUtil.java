package space.devport.modular;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;


@UtilityClass
public class FileUtil {

    @Nullable
    public <T> Class<? extends T> findClass(@NotNull final File file, @NotNull final Class<T> clazz)
            throws IOException, ClassNotFoundException {

        if (!file.exists()) {
            return null;
        }

        final URL jar = file.toURI().toURL();

        final List<String> matches = new ArrayList<>();
        final List<Class<? extends T>> classes = new ArrayList<>();

        try (final JarInputStream stream = new JarInputStream(jar.openStream());
             final URLClassLoader loader = new URLClassLoader(new URL[]{jar}, clazz.getClassLoader())) {

            JarEntry entry;
            while ((entry = stream.getNextJarEntry()) != null) {
                final String name = entry.getName();

                if (name == null || name.isEmpty() || !name.endsWith(".class")) {
                    continue;
                }

                matches.add(name.substring(0, name.lastIndexOf('.')).replace('/', '.'));
            }

            for (final String match : matches) {
                try {
                    final Class<?> loaded = loader.loadClass(match);
                    if (clazz.isAssignableFrom(loaded)) {
                        classes.add(loaded.asSubclass(clazz));
                    }
                } catch (final NoClassDefFoundError ignored) {
                }
            }
        }

        return classes.isEmpty() ? null : classes.get(0);
    }
}