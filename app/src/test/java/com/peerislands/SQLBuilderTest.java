package com.peerislands;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

class SQLBuilderTest {
    File file;

    @TempDir
    Path tempDir;

    @BeforeEach
    public void setUp() {
        try {
            file = Files.createTempFile(null, null).toFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write("{\"table\": \"employee\", \"columns\": [{\"name\": \"name\", \"table\": \"employee\"}]}");
            bw.close();
        } catch (InvalidPathException | IOException ipe) {
            System.err.println(
                    "error creating temporary test file in " +
                            this.getClass().getSimpleName());
        }
    }

    @Test
    void read() {
        SQLBuilder builder = new SQLBuilder();
        assertDoesNotThrow(() -> {
            builder.read(file.toPath().toString());
        });
        assertNotNull(builder.getRawContent(), "SQLBuilder should read the file");
        assertNotEquals(builder.getRawContent().length(), 0, "SQLBuilder should read more than 0 characters");
    }

    @Test
    void build() {
        SQLBuilder builder = new SQLBuilder();
        String filename = file.toPath().toString();

        assertDoesNotThrow(() -> {
            builder.type("json").read(filename).build();
        });
    }
}
