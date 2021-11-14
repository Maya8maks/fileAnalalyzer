package com.borshcheva.fileanalizer;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class FileAnalyzerTest {

    FileAnalyzer analyzer = new FileAnalyzer();
    String path = "test1.txt";
    File pathToFile = new File(path);
    String word = "InputStream";
    String content = "In this article, we explored simple ways to copy data from an InputStream to an OutputStream." +
            "The implementation of these examples is available over on GitHub.";

    @Test
    public void testGetContent() throws IOException {
        var result = FileAnalyzer.getContent(path);
        assertEquals("In this article, we explored simple ways to copy data from an InputStream to an OutputStream.", result);
    }

    @Test
    public void testGetContentFail() throws IOException {
        path = "test2.txt";
        assertThrows(FileNotFoundException.class, () -> {
            FileAnalyzer.getContent(path);
        });
    }

    @Test
    public void testGetSearchWordCount() throws IOException {
        var result = FileAnalyzer.getSearchWordCount(content, word);
        assertEquals(1, result);
    }
    @Test
    public void testGetSearchWordCountFail() throws IOException {
        var result = FileAnalyzer.getSearchWordCount(content, "File");
        assertEquals(0, result);
    }

    @Test
    public void testGetSplitSentences() throws IOException {
        content = "In this article, we explored simple ways to copy data from an InputStream to an OutputStream." +
                "The implementation of these examples is available over on GitHub.";
        var result = FileAnalyzer.getSplitSentences(content);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetSearchedSentences() throws IOException {

        var result = FileAnalyzer.getSearchedSentences(List.of(content), word);
        assertEquals(1, result.size());
    }

}
