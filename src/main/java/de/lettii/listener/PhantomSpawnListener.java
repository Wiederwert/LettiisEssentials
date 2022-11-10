package de.lettii.listener;

import com.destroystokyo.paper.event.entity.PhantomPreSpawnEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PhantomSpawnListener implements Listener {

    public  PhantomSpawnListener() {
        super();
    }

    @EventHandler
    public void onPhantomSpawn(final PhantomPreSpawnEvent event) {
        event.setCancelled(true);
    }
}
