package dev.rono.extensions.shared.config;

import dev.rono.igniscore.api.extension.ExtensionManifest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExtensionJarIntegrityTest {
    @ParameterizedTest
    @MethodSource("blockJars")
    void blockJarManifestMatchesStrategyClass(Path jarPath) throws Exception {
        assertJarManifest(jarPath, "block-extension.yml", "dev.rono.igniscore.block.");
    }

    @ParameterizedTest
    @MethodSource("itemJars")
    void itemJarManifestMatchesStrategyClass(Path jarPath) throws Exception {
        assertJarManifest(jarPath, "item-extension.yml", "dev.rono.igniscore.item.");
    }

    private static void assertJarManifest(Path jarPath, String manifestEntry, String strategyPrefix) throws Exception {
        try (JarFile jar = new JarFile(jarPath.toFile())) {
            JarEntry entry = jar.getJarEntry(manifestEntry);
            assertNotNull(entry, () -> manifestEntry + " missing from " + jarPath);
            assertTrue(entry.getSize() > 0, () -> manifestEntry + " is empty in " + jarPath);

            try (InputStream input = jar.getInputStream(entry)) {
                byte[] bytes = input.readAllBytes();
                assertTrue(bytes.length > 0, () -> manifestEntry + " has no content in " + jarPath);

                ExtensionManifest manifest = ExtensionManifest.fromStream(
                        new java.io.ByteArrayInputStream(bytes),
                        manifestEntry);

                String extensionId = jarPath.getFileName().toString().replaceFirst("(?i)\\.jar$", "");
                assertFalse(manifest.getId().isBlank());
                assertFalse(manifest.getStrategyClass().isBlank());
                assertTrue(manifest.getStrategyClass().startsWith(strategyPrefix),
                        () -> jarPath + " strategy must use " + strategyPrefix + ", got " + manifest.getStrategyClass());
                assertTrue(manifest.getStrategyClass().endsWith("." + toPackageSegment(extensionId) + ".Strategy"),
                        () -> jarPath + " strategy must match extension id " + extensionId
                                + ", got " + manifest.getStrategyClass());
            }

            JarEntry configEntry = jar.getJarEntry("config.yml");
            assertNotNull(configEntry, () -> "config.yml missing from " + jarPath);
            assertTrue(configEntry.getSize() > 0, () -> "config.yml is empty in " + jarPath);

            JarEntry sharedEntry = jar.getJarEntry("dev/rono/extensions/shared/ExtensionShared.class");
            assertNotNull(sharedEntry, () -> "ExtensionShared not embedded in " + jarPath);
        }
    }

    private static String toPackageSegment(String extensionId) {
        return extensionId.replace("-", "");
    }

    private static Stream<Path> blockJars() throws IOException {
        return packagedJars("blocks");
    }

    private static Stream<Path> itemJars() throws IOException {
        return packagedJars("items");
    }

    private static Stream<Path> packagedJars(String category) throws IOException {
        Path packsRoot = Path.of("..", "packs");
        if (!Files.isDirectory(packsRoot)) {
            return Stream.empty();
        }

        List<Path> jars = new ArrayList<>();
        try (Stream<Path> packs = Files.list(packsRoot)) {
            for (Path pack : packs.filter(Files::isDirectory).toList()) {
                Path categoryRoot = pack.resolve(category);
                if (!Files.isDirectory(categoryRoot)) {
                    continue;
                }
                try (Stream<Path> modules = Files.list(categoryRoot)) {
                    modules.map(module -> module.resolve("target").resolve(module.getFileName() + ".jar"))
                            .filter(Files::exists)
                            .forEach(jars::add);
                }
            }
        }
        return jars.stream();
    }
}
