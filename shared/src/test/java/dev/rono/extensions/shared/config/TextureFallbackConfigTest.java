package dev.rono.extensions.shared.config;

import dev.rono.igniscore.api.config.YamlDefinitions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TextureFallbackConfigTest {
    private static final Set<String> EXTENSION_FALLBACK_IDS = Set.of("grenade", "detonator");
    private static final Pattern NAMESPACED_FALLBACK = Pattern.compile("^(minecraft|igniscore):[a-z0-9_]+$");

    @ParameterizedTest
    @MethodSource("extensionConfigs")
    void texturesDeclareLogicalFallback(Path configPath) throws IOException {
        Map<String, Object> config = YamlDefinitions.loadMap(configPath);
        Object textures = config.get("textures");
        assertFalse(textures == null || !(textures instanceof Map<?, ?> map) || map.isEmpty(),
                () -> configPath + " missing textures section");

        @SuppressWarnings("unchecked")
        Map<String, Object> textureSection = (Map<String, Object>) textures;
        Object fallback = textureSection.get("fallback");
        assertFalse(fallback == null || fallback.toString().isBlank(),
                () -> configPath + " missing textures.fallback");

        String value = fallback.toString().trim();
        assertTrue(
                EXTENSION_FALLBACK_IDS.contains(value) || NAMESPACED_FALLBACK.matcher(value).matches(),
                () -> configPath + " textures.fallback must be minecraft:<id>, igniscore:<id>, or an extension id "
                        + EXTENSION_FALLBACK_IDS + " but was: " + value);
    }

    private static Stream<Path> extensionConfigs() throws IOException {
        Path packsRoot = Path.of("..", "packs");
        if (!Files.isDirectory(packsRoot)) {
            return Stream.empty();
        }
        List<Path> paths = new ArrayList<>();
        try (Stream<Path> packs = Files.list(packsRoot)) {
            for (Path pack : packs.filter(Files::isDirectory).toList()) {
                for (String category : List.of("blocks", "items")) {
                    Path categoryRoot = pack.resolve(category);
                    if (!Files.isDirectory(categoryRoot)) {
                        continue;
                    }
                    try (Stream<Path> modules = Files.list(categoryRoot)) {
                        modules.map(module -> module.resolve("src/main/resources/config.yml"))
                                .filter(Files::exists)
                                .forEach(paths::add);
                    }
                }
            }
        }
        return paths.stream();
    }
}
