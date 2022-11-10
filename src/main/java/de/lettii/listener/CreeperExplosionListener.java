package de.lettii.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class CreeperExplosionListener implements Listener {

    public CreeperExplosionListener() {
        super();
    }

    @EventHandler
    public void onCreeperExplosion(final ExplosionPrimeEvent event) {
        if (event.getEntityType() == EntityType.CREEPER) {
            event.setRadius(1.0f);
        }
    }
}
