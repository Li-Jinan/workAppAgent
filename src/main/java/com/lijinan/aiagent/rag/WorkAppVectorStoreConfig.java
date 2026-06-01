package com.lijinan.aiagent.rag;

import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 工作助手向量数据库配置（初始化基于内存的向量数据库 Bean）
 */
@Configuration
@ConditionalOnProperty(prefix = "workapp.rag.local-vector", name = "enabled", havingValue = "true", matchIfMissing = true)
public class WorkAppVectorStoreConfig {

    @Resource
    private WorkAppDocumentLoader workAppDocumentLoader;

    @Resource
    private MyTokenTextSplitter myTokenTextSplitter;

    @Resource
    private MyKeywordEnricher myKeywordEnricher;

    @Bean
    VectorStore workAppVectorStore(EmbeddingModel dashscopeEmbeddingModel) {
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(dashscopeEmbeddingModel).build();
        // 加载文档
        List<Document> documentList = workAppDocumentLoader.loadMarkdowns();
        // 自主切分文档
//        List<Document> splitDocuments = myTokenTextSplitter.splitCustomized(documentList);
        // 自动补充关键词元信息
        List<Document> enrichedDocuments = myKeywordEnricher.enrichDocuments(documentList);
        simpleVectorStore.add(enrichedDocuments);
        return simpleVectorStore;
    }
}
