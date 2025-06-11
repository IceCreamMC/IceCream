// This is a config mainly for satirical purposes, and it is not intended to be taken seriously.
package xyz.icecreammc.icecream;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import xyz.icecreammc.icecream.config.IceCreamConfig;

public class IslamMode {

    public static void tick(ServerPlayer player) {
        if (player == null) return;

        // Feature only enabled when islamMode is true
        if (!IceCreamConfig.getInstance().ISLAM_MODE.getValue())
            return;

        ItemStack activeItem = player.getUseItem();
        if (!player.isUsingItem()) return;

        if (activeItem.getItem() == Items.PORKCHOP ||
            activeItem.getItem() == Items.COOKED_PORKCHOP ||
            activeItem.getItem() == Items.ROTTEN_FLESH) {

                player.stopUsingItem();
                // Use the level's damageSources().generic() method
                DamageSource generic = player.level().damageSources().generic();
                player.hurt(generic, Float.MAX_VALUE); // Die for your culinary sins
            }
        }
    }
