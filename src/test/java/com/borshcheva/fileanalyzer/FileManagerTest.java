package com.borshcheva.fileanalizer;

import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;

public class FileManagerTest {
    @Test
    public void testCountFiles() throws IOException {
        Path path = Files.createTempDirectory("testTemp");

        assertEquals(0, FileManager.countFiles(path.toString()));

        Path path1 = Files.createTempDirectory(path, "temp1");
        Path path2 = Files.createTempDirectory(path1, "temp1");
        File.createTempFile("tmp1", ".txt", new File(path.toString()));

        assertEquals(1, FileManager.countFiles(path.toString()));

        File.createTempFile("tmp2", ".txt", new File(path1.toString()));
        File.createTempFile("tmp3", ".txt", new File(path2.toString()));

        assertEquals(3, FileManager.countFiles(path.toString()));
    }

    @Test
    public void testCountDirs() throws IOException {
        Path path = Files.createTempDirectory("temp");
        assertEquals(0, FileManager.countDirs(path.toString()));

        Path path1 = Files.createTempDirectory(path, "temp1");
        Path path2 = Files.createTempDirectory(path, "temp1");

        assertEquals(2, FileManager.countDirs(path.toString()));
        Path path3 = Files.createTempDirectory(path1, "tem31");

        assertEquals(3, FileManager.countDirs(path.toString()));

    }

    @Test
    public void testCopy() throws IOException {
        Path path = Files.createTempDirectory("testTemp1");

        assertEquals(0, FileManager.countFiles(path.toString()));

         Files.createTempDirectory(path, "temp1");
         Files.createTempDirectory(path, "temp2");
        File.createTempFile("tmp1", ".txt", new File(path.toString()));
        File.createTempFile("tmp2", ".txt", new File(path.toString()));
        File.createTempFile("tmp3", ".txt", new File(path.toString()));

        Path path2 = Files.createTempDirectory("testTemp2");

        FileManager.copy(path.toString(), path2.toString());
        assertEquals(3, FileManager.countFiles(path2.toString()));
        assertEquals(3, FileManager.countFiles(path.toString()));

    }
    @Test
    public void testMove() throws IOException {
        Path path = Files.createTempDirectory("testTemp1");

        assertEquals(0, FileManager.countFiles(path.toString()));

        Files.createTempDirectory(path, "temp1");
        Files.createTempDirectory(path, "temp2");
        File.createTempFile("tmp1", ".txt", new File(path.toString()));
        File.createTempFile("tmp2", ".txt", new File(path.toString()));
        File.createTempFile("tmp3", ".txt", new File(path.toString()));

        Path path2 = Files.createTempDirectory("testTemp2");

        FileManager.move(path.toString(), path2.toString());
        assertEquals(3, FileManager.countFiles(path2.toString()));
        assertEquals(0, FileManager.countFiles(path.toString()));

    }
}

