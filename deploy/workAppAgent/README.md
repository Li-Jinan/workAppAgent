# workAppAgent 独立部署说明

建议服务器上每个项目都用独立目录、独立 compose、独立容器名、独立数据库名和独立外部端口。

## 推荐服务器目录

```text
/opt/projects/
  workAppAgent/
    docker-compose.yml
    .env
  ai-code-agent/
    docker-compose.yml
    .env
  third-project/
    docker-compose.yml
    .env
```

## workAppAgent 当前约定

- 项目目录：`/opt/projects/workAppAgent`
- 前端容器：`work-app-agent-frontend`
- 后端容器：`work-app-agent-backend`
- Docker 网络：`work-app-agent-net`
- MySQL 数据库：`work_app_agent`
- MySQL 用户：`work_app_agent_user`
- Redis database：`1`
- 前端宿主机端口：`18081`
- 后端只在 Docker 内网暴露，由前端 Nginx 代理 `/api/`

## 和其他项目区分

| 项目 | 目录 | 数据库 | Redis DB | 前端端口 |
| --- | --- | --- | --- | --- |
| workAppAgent | `/opt/projects/workAppAgent` | `work_app_agent` | `1` | `18081` |
| ai-code-agent | `/opt/projects/ai-code-agent` | `ai_code_agent` | `2` | `18082` |
| third-project | `/opt/projects/third-project` | `third_project` | `3` | `18083` |

## 部署步骤

1. 在服务器创建目录：

```bash
mkdir -p /opt/projects/workAppAgent
```

2. 把本目录下的 `docker-compose.yml` 和 `.env.example` 上传到服务器目录。

3. 复制并编辑环境变量：

```bash
cp .env.example .env
```

4. 在 MySQL 中创建独立数据库和用户，数据库名建议保持 `work_app_agent`。

5. 启动：

```bash
docker compose up -d --build
```

6. 在 1Panel 网站反向代理里，把域名代理到：

```text
http://127.0.0.1:18081
```

## 注意

- 不要让多个项目共用同一个数据库名。
- 不要让多个项目共用同一个前端宿主机端口。
- 不要把 `.env` 提交到 GitHub。
- 如果你的 MySQL / Redis 是 1Panel 管理的宿主机服务，容器内应通过 `host.docker.internal` 访问宿主机。
