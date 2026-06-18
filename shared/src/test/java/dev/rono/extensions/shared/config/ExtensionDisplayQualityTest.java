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
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExtensionDisplayQualityTest {
    private static final Pattern TITLE_COLOR = Pattern.compile("^&[0-9a-fk-or]&l.+");
    private static final Pattern LORE_PREFIX = Pattern.compile("^&r&r&[0-9a-fk-or].+");
    private static final Pattern ACCENT_COLOR = Pattern.compile("&(?!7)[0-9a-fk-or]");
    private static final Pattern BROKEN_COLOR = Pattern.compile("&(?![0-9a-fk-orx#])");

    @ParameterizedTest
    @MethodSource("extensionConfigs")
    void displayHasTitleAndMeaningfulLore(Path configPath) throws IOException {
        Map<String, Object> config = YamlDefinitions.loadMap(configPath);
        @SuppressWarnings("unchecked")
        Map<String, Object> display = (Map<String, Object>) config.get("display");
        assertFalse(display == null || display.isEmpty(), () -> configPath + " missing display section");

        String title = String.valueOf(display.get("title"));
        assertFalse(title.isBlank(), () -> configPath + " missing display.title");
        assertTrue(TITLE_COLOR.matcher(title).matches(), () -> configPath + " title needs color + bold (&c&l...)");

        @SuppressWarnings("unchecked")
        List<String> description = (List<String>) display.get("description");
        assertFalse(description == null || description.isEmpty(), () -> configPath + " missing lore");
        assertTrue(description.size() >= 2, () -> configPath + " should have at least two lore lines");
        assertFalse(description.stream().anyMatch(line -> line.contains("Flare explosive")),
                () -> configPath + " still uses placeholder lore");
        assertFalse(description.stream().anyMatch(line -> line.contains("See item lore for usage hints")),
                () -> configPath + " still uses filler lore");
        assertTrue(description.stream().allMatch(line -> LORE_PREFIX.matcher(line).matches()),
                () -> configPath + " lore lines should start with &r&r and a color code");
        assertTrue(description.stream().anyMatch(ExtensionDisplayQualityTest::hasAccentColor),
                () -> configPath + " lore should highlight at least one detail with an accent color");
        assertFalse(description.stream().anyMatch(line -> BROKEN_COLOR.matcher(line).find()
                        && !line.contains("flint and steel")),
                () -> configPath + " lore contains a broken color code");
    }

    private static boolean hasAccentColor(String line) {
        return ACCENT_COLOR.matcher(line).find();
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
