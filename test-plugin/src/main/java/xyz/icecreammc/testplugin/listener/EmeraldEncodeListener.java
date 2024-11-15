package me.glicz.testplugin.listener;

import xyz.icecreammc.icecream.api.event.bus.EventHandler;
import xyz.icecreammc.icecream.api.event.packet.ItemStackEncodeEvent;
import xyz.icecreammc.icecream.api.item.ItemTypes;
import xyz.icecreammc.icecream.api.item.component.ItemComponentTypes;
import xyz.icecreammc.icecream.api.item.lore.ItemLore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.NotNull;

public class EmeraldEncodeListener implements EventHandler<ItemStackEncodeEvent> {
    @Override
    public void handle(@NotNull ItemStackEncodeEvent e) {
        if (e.getItemStack().getType() != ItemTypes.EMERALD) return;

        e.getItemStack().getItemComponentMap().set(ItemComponentTypes.LORE, ItemLore.itemLore(
                Component.text("This is ItemStackEncodeEvent showcase!", NamedTextColor.AQUA)
                        .decoration(TextDecoration.ITALIC, false),
                Component.text("All Emeralds come with this lore, however it's only visible client-side!", NamedTextColor.GREEN)
                        .decoration(TextDecoration.ITALIC, false)
        ));
    }
}
