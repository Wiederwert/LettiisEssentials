package de.lettii.listener;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerPostRespawnListener implements Listener {

    public  PlayerPostRespawnListener() { super(); }

    @EventHandler
    public void onPlayerRespawn(final PlayerPostRespawnEvent event) {
        Bukkit.getConsoleSender().sendMessage("Triggered!");
        if (event != null) {
//            sendMessage(event.getPlayer());
        }
    }

    private void sendMessage(final Player player) {
        if (player == null) {
            Bukkit.getConsoleSender().sendMessage("No player found for respawn event!");
            return;
        }
        final Location ldl = player.getLastDeathLocation();
        Bukkit.getConsoleSender().sendMessage("A0");
        if (ldl != null) {
            Bukkit.getConsoleSender().sendMessage("A1");
//            final String worldName = ldl.getWorld() == null ? "player.getWorld().getName()" : ldl.getWorld().getName();

//            final String textMessage = String.format("Gestorben bei %d %d %d in %s", ldl.getBlockX(), ldl.getBlockY(), ldl.getBlockZ(), worldName);
//            final String cmd = String.format("execute in %s run tp %s %f %f %f", worldName, player.getName(), ldl.getX(), ldl.getY(), ldl.getZ());

//        ClickEvent clickEvent = ClickEvent.runCommand(cmd);
//            TextComponent component = Component.empty()
//                .content(textMessage)
//                .clickEvent(clickEvent);
            player.sendMessage("component");
        }
    }
}
