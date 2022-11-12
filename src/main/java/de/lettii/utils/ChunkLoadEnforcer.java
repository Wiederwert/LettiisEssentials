package de.lettii.utils;

import de.lettii.dao.ChunkDAO;
import org.bukkit.Chunk;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ChunkLoadEnforcer {

    private final String CONFIG_KEEPCHUNKS_KEY = "keep-chunks";

    public void enforceLoadingChunk(Chunk chunk) {
        enforceLoadingChunks(chunk);
    }

    public void enforceLoadingChunk(Chunk chunk, boolean enforce) {
        enforceLoadingChunks(enforce, chunk);
    }

    public void enforceLoadingChunks(Chunk... chunks) {
        enforceLoadingChunks(Arrays.asList(chunks));
    }

    public void enforceLoadingChunks(boolean enforce, Chunk... chunks) {
        enforceLoadingChunks(Arrays.asList(chunks), enforce);
    }

    public void enforceLoadingChunks(Iterable<Chunk> chunks) {
        enforceLoadingChunks(chunks, true);
    }

    public void enforceLoadingChunks(Iterable<Chunk> chunks, boolean enforce) {
        if (chunks != null) {
            chunks.forEach(chunk -> {
                if (!chunk.isForceLoaded()) {
                    chunk.setForceLoaded(enforce);
                }
            });
        }
    }

    public int enforceLoadChunksFromConfig(@NotNull YamlConfiguration config) {
        List<?> rawList = config.getList(CONFIG_KEEPCHUNKS_KEY);
        int counter = 0;
        if (rawList != null) {
            List<Chunk> chunkList = rawList.stream()
                    .map(c -> ChunkDAO.deserialize((Map<String, Object>) c))
                    .filter(Objects::nonNull)
                    .map(ChunkDAO::getChunk)
                    .collect(Collectors.toList());
            for (Chunk chunk : chunkList) {
                if (!chunk.isForceLoaded()) {
                    chunk.setForceLoaded(true);
                    counter++;
                }
            }
        }

        return counter;
    }
}
