# Work App Agent

> 基于 Spring AI + Spring Boot + Vue 3 + Tool Calling + MCP 构建的 AI 工作助手与超级智能体应用。系统面向求职、职场沟通、工作效率和团队管理场景，支持多轮对话、流式响应、工具调用、RAG 知识库和 MCP 服务接入。

## 在线访问

| 类型 | 地址 |
| --- | --- |
| 前端应用 | [http://124.221.85.117:18081](http://124.221.85.117:18081) |
| 健康检查 | [http://124.221.85.117:18081/api/health](http://124.221.85.117:18081/api/health) |
| 接口文档 | [http://124.221.85.117:18081/api/doc.html](http://124.221.85.117:18081/api/doc.html) |
| OpenAPI JSON | [http://124.221.85.117:18081/api/v3/api-docs](http://124.221.85.117:18081/api/v3/api-docs) |

接口文档启用了 Basic Auth：

- 用户名：`root`
- 密码：通过服务器环境变量 `KNIFE4J_PASSWORD` 配置，请勿提交真实密码到 GitHub

## 功能

- AI 工作助手：支持求职、职场、管理等工作场景咨询
- 超级智能体：支持工具调用、文件操作、网页搜索、资源下载和 PDF 生成等能力
- RAG 知识库：内置求职、职场、管理方向的 Markdown 知识库
- SSE 流式对话：前后端通过 Server-Sent Events 实现流式输出

## 项目亮点

- 已完成前后端分离部署：Vue 3 静态资源由 Nginx 托管，后端 Spring Boot Jar 以生产环境配置启动。
- 接入 MySQL / Redis，支持应用对话、缓存和生产环境基础数据服务。
- 生产环境默认切换 DeepSeek，避免继续依赖阿里云 DashScope 产生额外按量费用。
- 提供 Knife4j 接口文档，便于调试、联调和展示后端接口能力。
- 支持同步对话、SSE 流式对话、Server-Sent Event 包装输出和 SseEmitter 输出。
- 基于 Tool Calling 和 MCP 扩展智能体能力，支持文件、终端、网页搜索、资源下载和 PDF 生成等工具。
- RAG / PgVector 相关代码保留但默认关闭，后续需要知识库检索时可通过配置重新启用。

## 技术栈

| 模块 | 技术 |
| --- | --- |
| 前端 | Vue 3、Vite、Vue Router、Axios |
| 后端 | Java 21、Spring Boot 3.4、Spring AI、LangChain4j、Knife4j |
| AI 能力 | DeepSeek、Tool Calling、MCP、RAG、Advisor |
| 存储 | MySQL、Redis、可选 PostgreSQL / PgVector |
| 部署 | Docker、Docker Compose、Nginx、1Panel、腾讯云服务器 |

## 本地启动

后端：

```bash
export DEEPSEEK_API_KEY=your_deepseek_api_key
mvn clean package -DskipTests
java -jar target/work-app-agent-0.0.1-SNAPSHOT.jar
```

前端：

```bash
cd work-app-agent-frontend
npm install
npm run dev
```

默认地址：

- 前端：http://localhost:3000
- 后端：http://localhost:8123/api
- 健康检查：http://localhost:8123/api/health

## 生产构建

后端：

```bash
mvn clean package -DskipTests
```

前端：

```bash
cd work-app-agent-frontend
npm run build
```

## 配置说明

敏感配置通过环境变量注入，避免提交真实密钥：

- `DEEPSEEK_API_KEY`
- `SEARCH_API_KEY`
- `PEXELS_API_KEY`
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `SPRING_REDIS_PASSWORD`
- `COS_HOST`
- `COS_SECRET_ID`
- `COS_SECRET_KEY`
- `COS_REGION`
- `COS_BUCKET`

上线前请在服务器或容器环境中配置真实值。

默认部署不启用阿里云 DashScope、云 RAG 和 PgVector，避免产生额外按量费用。后续需要恢复 PgVector 时，可打开：

```yaml
workapp:
  rag:
    pgvector:
      enabled: true
```

## 部署记录

- 前端：构建 `dist` 后通过 Nginx 容器托管静态资源。
- 后端：使用生产环境配置启动 Spring Boot Jar。
- 反向代理：前端 Nginx 将 `/api` 请求转发到后端服务。
- 数据服务：MySQL 保存业务数据，Redis 提供缓存支持。
- 访问端口：腾讯云安全组和 1Panel 防火墙已放行 `18081`。
- 文档入口：Knife4j 接口文档已通过线上地址访问。

## 适合简历描述

基于 Spring AI + Spring Boot + Vue 3 构建 AI 工作助手与超级智能体应用，支持多轮对话记忆、SSE 流式响应、Tool Calling、MCP 工具服务、RAG 知识库扩展和前后端分离部署，实现从工作场景咨询到智能体工具调用的完整链路。
