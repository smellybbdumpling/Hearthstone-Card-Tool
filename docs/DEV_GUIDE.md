# 开发指南

## 环境要求

- JDK 17
- Maven 3.9+
- Node.js 20+
- MySQL 8
- Redis 6+

## 配置

后端配置文件位于：

```text
backend/src/main/resources/application.yml
```

仓库中的配置使用环境变量占位，真实密码和密钥不要提交到 GitHub。可以参考：

```text
.env.example
backend/src/main/resources/application.example.yml
```

常用环境变量：

- `MYSQL_URL`
- `MYSQL_USERNAME`
- `MYSQL_PASSWORD`
- `REDIS_HOST`
- `REDIS_PORT`
- `REDIS_PASSWORD`
- `JWT_SECRET`
- `CORS_ALLOWED_ORIGINS`

如果本地 Redis 没有密码，`REDIS_PASSWORD` 留空即可。当前卡牌查询、卡牌详情和收藏列表都直接查 MySQL，不依赖 Redis 缓存。

## MySQL 和 seed 数据

项目随附 `schema.sql` 和 `cards_seed.sql`。普通后端启动不会自动执行 seed SQL，会直接使用数据库中已有的卡牌数据。

内置卡牌数据仅用于个人学习和功能演示。相关卡牌名称、文本、图片、商标和其他游戏内容归其权利方所有，不包含在本项目 MIT 许可证授权范围内。

首次建库或需要显式重建内置数据时，使用 `init` profile 启动一次：

```bash
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=init
```

该 profile 会执行：

- `backend/src/main/resources/db/schema.sql`
- `backend/src/main/resources/db/cards_seed.sql`

执行完成后，数据库会包含 1032 张内置标准卡牌。后续普通启动不会反复覆盖数据库。

## 后端开发

启动：

```bash
cd backend
mvn spring-boot:run
```

测试：

```bash
cd backend
mvn test
```

默认后端端口：

```text
8080
```

## 前端开发

安装依赖：

```bash
cd frontend
npm install
```

启动：

```bash
npm run dev
```

构建：

```bash
npm run build
```

默认前端端口：

```text
5173
```

前端请求 `/api`，由 Vite 代理到 `http://localhost:8080`。

## 验证流程

1. 启动 MySQL 和 Redis。
2. 使用 `init` profile 初始化数据库，或确认数据库已有 seed 数据。
3. 启动后端。
4. 启动前端并打开 `http://localhost:5173`。
5. 首页应自动展示第一页卡牌。
6. 打开任意卡牌详情，确认描述、趣味文本和画家信息正常显示。
7. 注册并登录测试账号。
8. 收藏卡牌，再进入收藏页确认列表正常显示。

## 注意事项

- 应用运行时不提供数据导入入口，卡牌数据来自内置 seed 文件。
- 图片只保存 URL，不批量下载图片文件。
- 重建 seed 后，建议重启后端并确认连接的是目标数据库。
- 上传公开仓库前，确认没有提交真实数据库密码、Redis 密码或 JWT 密钥。
