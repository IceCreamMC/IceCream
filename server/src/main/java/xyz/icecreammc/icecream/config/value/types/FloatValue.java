package xyz.icecreammc.icecream.config.value.types;

import org.jetbrains.annotations.NotNull;
import xyz.icecreammc.icecream.config.value.Value;

public class FloatValue extends Value<Float> {

    public FloatValue(@NotNull String path, float defaultValue, @NotNull String description) {
        super(path, defaultValue, description);
    }

    public FloatValue(@NotNull String path, float defaultValue) {
        super(path, defaultValue);
    }

    public void setValue(String value) {
        type = Float.parseFloat(value);
    }
}
