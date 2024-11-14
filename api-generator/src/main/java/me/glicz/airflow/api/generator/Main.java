package xyz.icecreammc.icecream.api.generator;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import xyz.icecreammc.icecream.api.generator.block.BlockTypesGenerator;
import xyz.icecreammc.icecream.api.generator.block.state.BlockStatePropertiesGenerator;
import xyz.icecreammc.icecream.api.generator.entity.EntityTypesGenerator;
import xyz.icecreammc.icecream.api.generator.inventory.menu.MenuTypesGenerator;
import xyz.icecreammc.icecream.api.generator.item.ItemTypesGenerator;
import xyz.icecreammc.icecream.api.generator.item.component.ItemComponentTypesGenerator;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;

import java.io.File;
import java.io.IOException;

public class Main {
    public static boolean DEBUG;

    public static void main(String[] args) throws IOException {
        OptionParser optionParser = new OptionParser();
        OptionSpec<File> sourceFolderOption = optionParser.accepts("sourceFolder").withRequiredArg().ofType(File.class);
        OptionSpec<Void> debug = optionParser.accepts("debug");

        OptionSet optionSet = optionParser.parse(args);
        File sourceFolder = optionSet.valueOf(sourceFolderOption);
        DEBUG = optionSet.has(debug);

        SharedConstants.tryDetectVersion();
        String version = SharedConstants.getCurrentVersion().getName();

        Bootstrap.bootStrap();
        Bootstrap.validate();

        new BlockStatePropertiesGenerator().run(version, sourceFolder);
        new BlockTypesGenerator().run(version, sourceFolder);
        new EntityTypesGenerator().run(version, sourceFolder);
        new MenuTypesGenerator().run(version, sourceFolder);
        new ItemComponentTypesGenerator().run(version, sourceFolder);
        new ItemTypesGenerator().run(version, sourceFolder);
    }
}
