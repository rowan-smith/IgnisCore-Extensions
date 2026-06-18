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
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExtensionDisplayQualityTest {
    @ParameterizedTest
    @MethodSource("extensionConfigs")
    void displayHasTitleAndMeaningfulLore(Path configPath) throws IOException {
        Map<String, Object> config = YamlDefinitions.loadMap(configPath);
        @SuppressWarnings("unchecked")
        Map<String, Object> display = (Map<String, Object>) config.get("display");
        assertFalse(display == null || display.isEmpty(), () -> configPath + " missing display section");

        String title = String.valueOf(display.get("title"));
        assertFalse(title.isBlank(), () -> configPath + " missing display.title");

        @SuppressWarnings("unchecked")
        List<String> description = (List<String>) display.get("description");
        assertFalse(description == null || description.isEmpty(), () -> configPath + " missing lore");
        assertTrue(description.size() >= 2, () -> configPath + " should have at least two lore lines");
        assertFalse(description.stream().anyMatch(line -> line.contains("Flare explosive")),
                () -> configPath + " still uses placeholder lore");
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
