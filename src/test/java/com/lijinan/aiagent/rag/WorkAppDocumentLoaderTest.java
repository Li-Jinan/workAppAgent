package com.lijinan.aiagent.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WorkAppDocumentLoaderTest {

    @Resource
    private WorkAppDocumentLoader workAppDocumentLoader;

    @Test
    void loadMarkdowns() {
        workAppDocumentLoader.loadMarkdowns();
    }
}