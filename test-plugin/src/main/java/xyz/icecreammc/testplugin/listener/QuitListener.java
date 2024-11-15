package me.glicz.testplugin.listener;

import xyz.icecreammc.icecream.api.event.bus.EventHandler;
import xyz.icecreammc.icecream.api.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class QuitListener implements EventHandler<PlayerQuitEvent> {
    @Override
    public void handle(@NotNull PlayerQuitEvent e) {
        e.setMessage(null);
        e.getPlayer().getServer().getServerCommandSender().sendMessage("Bye " + e.getPlayer().getName());
    }
}
