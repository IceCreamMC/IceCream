package xyz.icecreammc.icecream.config;

import org.jetbrains.annotations.NotNull;
import xyz.icecreammc.icecream.config.value.Value;
import xyz.icecreammc.icecream.config.value.types.BooleanValue;
import xyz.icecreammc.icecream.config.value.types.IntValue;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class IceCreamConfig extends AbstractConfig {

    private static final IceCreamConfig INSTANCE;

    static {
        INSTANCE = new IceCreamConfig("icecream.yml");
    }

    public static IceCreamConfig getInstance() {
        return INSTANCE;
    }

    public IceCreamConfig(@NotNull String fileName) {
        super(new String[] {
                "This is the main configuration file for IceCream!",
                "",
                "Docs: COMING SOON!",
                "Github: https://github.com/IceCreamMC/",
                "Discord: https://discord.gg/hFTr5FR4Va",
                ""
        }, fileName, 2);
        load();
    }

    // Config Values
    public final BooleanValue SHEAR_IN_DISPENSER_UNLIMITED_USE = new BooleanValue("misc.shear-in-dispenser-unlimited-use", false, "If true when a shear is in a dispenser it can be used forever.");
    // Config Values
}
