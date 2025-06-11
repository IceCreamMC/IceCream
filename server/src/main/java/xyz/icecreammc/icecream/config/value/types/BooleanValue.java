package xyz.icecreammc.icecream.config.value.types;

import org.jetbrains.annotations.NotNull;
import xyz.icecreammc.icecream.config.value.Value;

public class BooleanValue extends Value<Boolean> {

    public BooleanValue(@NotNull String path, boolean defaultValue, @NotNull String description) {
        super(path, defaultValue, description);
    }

    public BooleanValue(@NotNull String path, boolean defaultValue) {
        super(path, defaultValue);
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof Boolean) {
            type = (Boolean) value;
        } else if (value instanceof String) {
            type = Boolean.parseBoolean((String) value);
        } else if (value == null) {
            type = false;
        } else {
            throw new IllegalArgumentException("Unsupported value type for BooleanValue: " + value.getClass());
        }
    }

    @Override
    public void setValue(String value) {
        type = value != null && Boolean.parseBoolean(value);
    }
}
