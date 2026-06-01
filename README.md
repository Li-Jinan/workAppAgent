# Work App Agent

Work App Agent 是一个面向求职、职场沟通、工作效率和团队管理场景的 AI 工作助手项目。项目包含 Spring Boot 后端、Vue 3 前端，以及一个可选的图片搜索 MCP Server。

## 功能

- AI 工作助手：支持求职、职场、管理等工作场景咨询
- 超级智能体：支持工具调用、文件操作、网页搜索、资源下载和 PDF 生成等能力
- RAG 知识库：内置求职、职场、管理方向的 Markdown 知识库
- SSE 流式对话：前后端通过 Server-Sent Events 实现流式输出

## 技术栈

- 后端：Java 21、Spring Boot 3.4、Spring AI、Spring AI Alibaba
- 前端：Vue 3、Vite、Vue Router、Axios
- 可选组件：PostgreSQL / PgVector、MCP Server

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
