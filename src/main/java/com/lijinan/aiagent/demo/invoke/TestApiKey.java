package com.lijinan.aiagent.demo.invoke;

/**
 * 仅用于测试获取 API Key
 */
public interface TestApiKey {

    // 通过环境变量配置 API Key，避免把密钥提交到仓库
    String API_KEY = System.getenv("DASHSCOPE_API_KEY");
}
