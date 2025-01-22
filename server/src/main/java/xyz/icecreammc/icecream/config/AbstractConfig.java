package xyz.icecreammc.icecream.config;

import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.icecreammc.icecream.config.value.Value;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.LoaderOptions;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public abstract class AbstractConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractConfig.class);

    private final String[] header;
    public final File file;
    private final int configVersion;

    private Map<String, Object> configData;
    private List<Value<?>> configValues;

    private final Yaml yaml;

    public AbstractConfig(@Nullable String[] header, @NotNull String fileName, int configVersion) {
        Validate.notNull(fileName, "fileName cannot be null");
        Validate.notBlank(fileName, "fileName cannot be blank");

        this.header = header;
        this.file = new File(fileName);
        this.configVersion = configVersion;

        // Configure SnakeYAML
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setIndent(2);
        dumperOptions.setPrettyFlow(true);
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Representer representer = new Representer(dumperOptions);
        LoaderOptions loaderOptions = new LoaderOptions();

        this.yaml = new Yaml(new Constructor(loaderOptions), representer, dumperOptions);
    }

    public AbstractConfig(String fileName, int configVersion) {
        this(null, fileName, configVersion);
    }

    public void load() {
        if (configData != null) return;

        // Load YAML configuration
        if (file.exists()) {
            try (InputStream inputStream = new FileInputStream(file)) {
                configData = yaml.load(inputStream);
                if (configData == null) {
                    configData = new HashMap<>();
                }
            } catch (IOException e) {
                LOGGER.error("Failed to load configuration file: {}", file.getAbsolutePath(), e);
                configData = new HashMap<>();
            }
        } else {
            configData = new HashMap<>();
        }

        // Check for version mismatch and update defaults
        int currentVersion = (int) configData.getOrDefault("config-version", 0);
        if (currentVersion != configVersion) {
            Map<String, Object> defaultConfig = new HashMap<>();
            collectConfigValues().forEach(v -> defaultConfig.put(v.getPath(), v.getValue()));

            // Merge defaults with existing config
            defaultConfig.forEach((key, value) -> {
                if (!configData.containsKey(key) || configData.get(key) == null) {
                    configData.put(key, value);
                }
            });

            configData.put("config-version", configVersion);
            save();
        }

        // Populate values
        collectConfigValues().forEach(v -> {
            Object value = configData.get(v.getPath());
            if (value instanceof String || value == null) {
                v.setValue((String) value);
            } else {
                LOGGER.warn("Expected String for path '{}', but found: {}", v.getPath(), value.getClass().getSimpleName());
            }
        });

        save();
    }

    public void save() {
        try (Writer writer = new FileWriter(file)) {
            // Add header if specified
            if (header != null) {
                writer.write("# " + String.join("\n# ", header) + "\n");
            }

            // Write YAML content
            yaml.dump(configData, writer);
        } catch (IOException e) {
            LOGGER.error("Failed to save configuration file: {}", file.getAbsolutePath(), e);
        }
    }

    private List<Value<?>> collectConfigValues() {
        if (configValues != null)
            return configValues;

        configValues = new ArrayList<>();

        for (Field field : getClass().getDeclaredFields()) {
            if (Value.class.isAssignableFrom(field.getType())) {
                try {
                    if (!field.trySetAccessible()) {
                        LOGGER.warn("Could not set '{}' accessible", field.getName());
                        continue;
                    }
                    final Value<?> v = (Value<?>) field.get(this);
                    if (v == null) continue;
                    configValues.add(v);
                    configData.putIfAbsent(v.getPath(), v.getValue());
                } catch (IllegalAccessException e) {
                    LOGGER.warn("Could not read '{}'", field.getName(), e);
                }
            }
        }

        LOGGER.info("Loaded {} config values", configValues.size());
        return configValues;
    }
}
