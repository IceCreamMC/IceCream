package xyz.icecreammc.icecream.api.generator.block;

import xyz.icecreammc.icecream.api.block.BlockType;
import xyz.icecreammc.icecream.api.block.BlockTypeProvider;
import xyz.icecreammc.icecream.api.generator.RegistryBasedGenerator;
import net.minecraft.core.registries.BuiltInRegistries;

public class BlockTypesGenerator extends RegistryBasedGenerator {
    public BlockTypesGenerator() {
        super(BuiltInRegistries.BLOCK, "xyz.icecreammc.icecream.api.block", "BlockTypes", BlockType.class, BlockTypeProvider.class);
    }
}
