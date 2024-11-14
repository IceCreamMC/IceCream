package xyz.icecreammc.icecream.api.generator.item;

import xyz.icecreammc.icecream.api.generator.RegistryBasedGenerator;
import xyz.icecreammc.icecream.api.item.ItemType;
import xyz.icecreammc.icecream.api.item.ItemTypeProvider;
import net.minecraft.core.registries.BuiltInRegistries;

public class ItemTypesGenerator extends RegistryBasedGenerator {
    public ItemTypesGenerator() {
        super(BuiltInRegistries.ITEM, "xyz.icecreammc.icecream.api.item", "ItemTypes", ItemType.class, ItemTypeProvider.class);
    }
}
