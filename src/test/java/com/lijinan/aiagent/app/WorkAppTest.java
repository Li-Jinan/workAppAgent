package com.lijinan.aiagent.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class WorkAppTest {

    @Resource
    private WorkApp workApp;

    @Test
    void testChat() {
        String chatId = UUID.randomUUID().toString();
        // 第一轮
        String message = "你好，我是用户";
        String answer = workApp.doChat(message, chatId);
        // 第二轮
        message = "我想优化简历，让编程导航这个项目更适合求职展示";
        answer = workApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
        // 第三轮
        message = "我刚才提到的项目叫什么来着？帮我回忆一下";
        answer = workApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithReport() {
        String chatId = UUID.randomUUID().toString();
        String message = "你好，我是用户，我想把编程导航项目写进简历，但不知道怎么突出亮点";
        WorkApp.WorkReport workReport = workApp.doChatWithReport(message, chatId);
        Assertions.assertNotNull(workReport);
    }

    @Test
    void doChatWithRag() {
        String chatId = UUID.randomUUID().toString();
        String message = "我在团队协作中经常遇到需求反复，应该怎么沟通？";
        String answer = workApp.doChatWithRag(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithTools() {
        // 测试联网搜索问题的答案
        testMessage("帮我搜索 Java 后端工程师面试高频题，并按模块整理重点");

        // 测试网页抓取：工作案例分析
        testMessage("看看编程导航网站（codefather.cn）有哪些适合写进简历的项目亮点？");

        // 测试资源下载：图片下载
        testMessage("直接下载一张适合做工作汇报封面的科技风图片为文件");

        // 测试终端操作：执行代码
        testMessage("执行 Python3 脚本来生成数据分析报告");

        // 测试文件操作：保存用户档案
        testMessage("保存我的工作档案为文件");

        // 测试 PDF 生成
        testMessage("生成一份‘求职准备计划’PDF，包含简历优化、面试复习和项目复盘清单");
    }

    private void testMessage(String message) {
        String chatId = UUID.randomUUID().toString();
        String answer = workApp.doChatWithTools(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithMcp() {
        String chatId = UUID.randomUUID().toString();
        // 测试地图 MCP
//        String message = "我在上海静安区工作，请帮我找到 5 公里内适合远程办公的地点";
//        String answer =  workApp.doChatWithMcp(message, chatId);
//        Assertions.assertNotNull(answer);
        // 测试图片搜索 MCP
        String message = "帮我搜索一些适合工作汇报封面的图片";
        String answer =  workApp.doChatWithMcp(message, chatId);
        Assertions.assertNotNull(answer);
    }
}
