package de.lettii.dao;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ChunkDAO implements ConfigurationSerializable {

    private Chunk chunk;

    public ChunkDAO(@NotNull Chunk chunk) {
        chunk = chunk;
    }

    @NotNull
    public Chunk getChunk() {
        return chunk;
    }

    public void setChunk(@NotNull Chunk chunk) {
        this.chunk = chunk;
    }

    @Override
    @NotNull
    public Map<String, Object> serialize() {
        Map<String, Object> serialized = new HashMap<>();

        Map<String, Object> chunkInfo = new HashMap<>();
        chunkInfo.put("X", chunk.getX());
        chunkInfo.put("Z", chunk.getZ());
        chunkInfo.put("world", chunk.getWorld().getName());

        serialized.put(String.valueOf(chunk.getChunkKey()), chunkInfo);
        return serialized;
    }

    public static ChunkDAO deserialize(@NotNull Map<String, Object> serialized) {
        Chunk chunk = null;
        if (serialized.size() == 1) {
            Map<String, ?> chunkInfo = (Map<String, ?>) serialized.values().stream().toArray()[0];
            long chunkId = Long.getLong(String.valueOf(serialized.keySet().stream().toArray()[0]));
            if (chunkInfo.containsKey("X") && chunkInfo.containsKey("Z") && chunkInfo.containsKey("world")) {
                World world = Bukkit.getWorld((String) chunkInfo.get("world"));
                if (world != null) {
                    int x = Integer.parseInt((String) chunkInfo.get("X"));
                    int z = Integer.parseInt((String) chunkInfo.get("Z"));
                    Chunk checkChunk = world.getChunkAt(x, z);
                    if (checkChunk.getChunkKey() == chunkId) {
                        chunk = checkChunk;
                    }
                }
            }
        }

        return chunk == null ? null : new ChunkDAO(chunk);
    }
}
