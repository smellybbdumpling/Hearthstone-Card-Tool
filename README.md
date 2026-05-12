# Hearthstone Card Tool

炉石传说标准环境卡牌查询与收藏工具。项目采用前后端分离结构：后端位于 `backend`，前端位于 `frontend`，文档位于 `docs`。

## 功能概览

- 首页自动加载标准环境卡牌。
- 默认按系列顺序、费用、中文名排序。
- 支持按名称、系列、职业、稀有度、类型、费用筛选。
- 支持分页查询，默认每页 15 张。
- 支持卡牌详情弹窗，展示图片、中文名、英文名、系列、职业、类型、派系 / 种族、费用、稀有度、攻击、生命 / 耐久、描述、趣味文本和画家。
- 支持复制中文名和英文名。
- 支持用户注册、登录、退出。
- 登录后可收藏、取消收藏，并在收藏页查看收藏列表。

## 技术栈

后端：

- Java 17
- Spring Boot 3
- Spring Security
- JWT
- MyBatis-Plus
- MySQL 8
- Redis

前端：

- Vue 3
- Vite
- TypeScript
- Pinia
- Vue Router
- Axios
- Element Plus

## 项目结构

```text
backend/   Spring Boot 后端
frontend/  Vue 3 前端
docs/      接口、数据库和开发说明
```

## 本地启动

环境要求：

- JDK 17
- Maven 3.9+
- Node.js 20+
- MySQL 8
- Redis 6+

准备配置：

1. 启动 MySQL 和 Redis。
2. 复制 `.env.example` 中的配置，按本机环境设置环境变量。
3. 如果本地 Redis 没有密码，`REDIS_PASSWORD` 可以留空。
4. `JWT_SECRET` 建议替换为至少 32 字节的随机字符串。

后端：

```bash
cd backend
mvn spring-boot:run
```

前端：

```bash
cd frontend
npm install
npm run dev
```

访问：

```text
http://localhost:5173
```

Vite 会把 `/api` 代理到 `http://localhost:8080`。

## 数据初始化

项目已随附标准环境卡牌 seed 数据。首次建库或需要重建内置卡牌数据时，使用 `init` profile 启动一次后端：

```bash
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=init
```

`application-init.yml` 会执行：

- `classpath:db/schema.sql`
- `classpath:db/cards_seed.sql`

执行完成后，数据库会包含当前标准环境的 1032 张卡牌。普通启动使用 `application.yml`，`spring.sql.init.mode` 默认为 `never`，不会反复重建表或覆盖数据。

内置数据文件：

- `backend/src/main/resources/data/cards_seed.json`
- `backend/src/main/resources/db/cards_seed.sql`

应用运行时不访问外部数据源，也不下载卡牌图片文件；数据库只保存图片 URL。

## 标准系列范围

当前按以下顺序展示和排序：

1. 核心2026
2. 治愈艾泽拉斯
3. 大地的裂变
4. 永恒回响
5. 穿越时间流
6. 重生之日
7. 安戈洛龟途
8. 世界之树的余烬
9. 漫游翡翠梦境

## 配置说明

主要配置位于 `backend/src/main/resources/application.yml`。仓库内只保留占位值，真实值建议通过环境变量提供。

常用变量：

| 变量 | 说明 | 默认值 |
| --- | --- | --- |
| `MYSQL_URL` | MySQL JDBC 地址 | 本地 `hearthstone_cards` |
| `MYSQL_USERNAME` | MySQL 用户名 | `root` |
| `MYSQL_PASSWORD` | MySQL 密码 | `change_me` |
| `REDIS_HOST` | Redis 地址 | `localhost` |
| `REDIS_PORT` | Redis 端口 | `6379` |
| `REDIS_PASSWORD` | Redis 密码 | 空 |
| `JWT_SECRET` | JWT 签名密钥 | 占位值 |
| `CORS_ALLOWED_ORIGINS` | 允许跨域的前端地址 | `http://localhost:5173` |

## 验证命令

后端测试：

```bash
cd backend
mvn test
```

前端构建：

```bash
cd frontend
npm run build
```

## 文档

- [接口文档](docs/API.md)
- [数据库说明](docs/DATABASE.md)
- [开发指南](docs/DEV_GUIDE.md)

## 许可证与说明

本仓库代码采用 [MIT License](LICENSE)。

本项目仅用于个人学习与技术交流，不用于商业用途。炉石传说、Hearthstone、相关卡牌名称、文本、图片、商标和其他游戏内容归其权利方所有，不包含在本仓库代码许可证授权范围内。

## 后续计划

- 卡牌列表展示收藏状态。
- 收藏按钮根据状态显示“收藏 / 已收藏”。
- 优化登录状态恢复。
- 增加 Docker Compose 一键启动。
- 增加自动化接口测试。
- 增加搜索关键词高亮。
- 增加按费用、稀有度、职业统计卡牌数量。
