package xyz.icecreammc.icecream.config;

import org.jetbrains.annotations.NotNull;
import xyz.icecreammc.icecream.config.value.Value;
import xyz.icecreammc.icecream.config.value.types.BooleanValue;
import xyz.icecreammc.icecream.config.value.types.IntValue;
import xyz.icecreammc.icecream.config.value.types.StringValue;

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
    public final StringValue SERVER_MOD_NAME = new StringValue("settings.server-mod-name", "IceCream", "Lets you change the server mod name.");
    public final BooleanValue ISLAM_MODE = new BooleanValue("fun.islam-mode", false, "If true, eating pork or rotten flesh will instantly kill you."); // THIS IS FOR SATIRICAL REASONS. WE RESPECT ALL CULTURES :)
    // Config Values
}
