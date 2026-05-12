# 数据库说明

数据库名：`hearthstone_cards`

初始化脚本：

- `backend/src/main/resources/db/schema.sql`：建表。
- `backend/src/main/resources/db/cards_seed.sql`：内置标准卡牌数据。

当前随附 seed 数据共 1032 张。`cards_seed.sql` 中的描述和趣味文本已保存为纯文本，前端详情页可以直接渲染，不需要使用 `v-html`。

这些内置数据仅用于个人学习和功能演示。炉石传说相关卡牌名称、文本、图片、商标和其他游戏内容归其权利方所有，不包含在本项目 MIT 许可证授权范围内。

`cards_seed.sql` 执行前会清理旧的 `iyingdi-%` 和 `iyingdi-first10-%` 卡牌数据，并同步删除这些卡牌关联的收藏记录，避免历史示例数据污染正式数据。

## users

用户表。

| 字段 | 说明 |
| --- | --- |
| `id` | 主键 |
| `username` | 登录用户名，唯一 |
| `password` | BCrypt 加密后的密码 |
| `nickname` | 昵称 |
| `created_at` | 创建时间 |
| `updated_at` | 更新时间 |

## cards

卡牌表。

| 字段 | 说明 |
| --- | --- |
| `id` | 主键 |
| `card_id` | 外部或 seed 卡牌编号，唯一 |
| `name_cn` | 中文名 |
| `name_en` | 英文名 |
| `card_set` | 所属系列 |
| `card_class` | 职业 |
| `rarity` | 稀有度 |
| `card_type` | 类型 |
| `spell_school` | 法术派系或随从种族 |
| `cost` | 法力值费用 |
| `attack` | 攻击力 |
| `health` | 生命值 |
| `durability` | 武器耐久 |
| `description` | 卡牌描述，seed 中保存为纯文本 |
| `flavor_text` | 趣味文本，seed 中保存为纯文本 |
| `artist` | 画家 |
| `image_url` | 图片地址，只保存 URL，不保存图片文件 |
| `standard_legal` | 是否标准可用 |
| `created_at` | 创建时间 |
| `updated_at` | 更新时间 |

`card_set` 当前按以下顺序展示和查询排序：

1. 核心2026
2. 治愈艾泽拉斯
3. 大地的裂变
4. 永恒回响
5. 穿越时间流
6. 重生之日
7. 安戈洛龟途
8. 世界之树的余烬
9. 漫游翡翠梦境

## user_favorites

用户收藏表。

| 字段 | 说明 |
| --- | --- |
| `id` | 主键 |
| `user_id` | 用户 ID |
| `card_id` | 卡牌 ID，对应 `cards.id` |
| `created_at` | 收藏时间 |

## card_data_version

内置卡牌数据版本表。

| 字段 | 说明 |
| --- | --- |
| `version` | 数据版本 |
| `generated_at` | 数据生成时间 |
| `source_url` | 数据来源 URL |
| `series_scope` | 系列范围 |
| `total_cards` | 卡牌总数 |
| `checksum` | seed JSON 校验摘要 |

## 初始化策略

普通启动使用 `application.yml`，`spring.sql.init.mode` 默认为 `never`，不会自动重建表和 seed 数据。

首次建库或需要重建内置卡牌数据时，使用 `init` profile：

```bash
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=init
```

该 profile 会执行 `schema.sql` 和 `cards_seed.sql`。执行完成后，数据库会包含 1032 张内置标准卡牌。
