# API 文档

后端接口统一挂在 `/api` 下。公开接口可以直接访问，收藏和当前用户相关接口需要登录后携带 JWT。

统一响应格式：

```json
{
  "code": 0,
  "message": "ok",
  "data": {}
}
```

请求失败时 `code` 非 0，`message` 返回错误说明。

## 认证

### 注册

`POST /api/auth/register`

请求体：

```json
{
  "username": "demo",
  "password": "your_password",
  "nickname": "炉石玩家"
}
```

### 登录

`POST /api/auth/login`

请求体：

```json
{
  "username": "demo",
  "password": "your_password"
}
```

返回用户信息和 `token`。后续需要登录的接口使用请求头：

```text
Authorization: Bearer <token>
```

### 当前用户

`GET /api/auth/me`

需要登录。

## 卡牌

### 分页查询

`GET /api/cards`

查询参数：

| 参数 | 说明 |
| --- | --- |
| `keyword` | 中文名或英文名关键词 |
| `cardSet` | 系列 |
| `cardClass` | 职业 |
| `rarity` | 稀有度 |
| `cardType` | 类型 |
| `cost` | 费用 |
| `page` | 页码，默认从 1 开始 |
| `size` | 每页数量，前端默认 15 |

不传筛选条件时返回全部标准卡牌，并按系列顺序、费用、中文名排序。

卡牌字段：

| 字段 | 说明 |
| --- | --- |
| `id` | 数据库主键 |
| `cardId` | 来源卡牌 ID |
| `nameCn` | 中文名 |
| `nameEn` | 英文名 |
| `cardSet` | 所属系列 |
| `cardClass` | 职业 |
| `rarity` | 稀有度 |
| `cardType` | 类型 |
| `spellSchool` | 法术派系或随从种族 |
| `cost` | 法力值费用 |
| `attack` | 攻击力 |
| `health` | 生命值 |
| `durability` | 武器耐久 |
| `description` | 卡牌描述，seed 中已清洗 HTML 标签 |
| `flavorText` | 趣味文本，seed 中已清洗 HTML 标签 |
| `artist` | 画家 |
| `imageUrl` | 图片 URL |
| `standardLegal` | 是否标准可用 |

### 详情

`GET /api/cards/{id}`

返回单张卡牌的完整字段。前端详情弹窗会展示图片、基础属性、描述、趣味文本和画家信息。

## 收藏

### 收藏卡牌

`POST /api/favorites/{cardId}`

需要登录。这里的 `cardId` 是数据库卡牌主键 `id`。

### 取消收藏

`DELETE /api/favorites/{cardId}`

需要登录。

### 收藏列表

`GET /api/favorites?page=1&size=15`

需要登录。当前收藏列表直接查询 MySQL，不使用 Redis 缓存。

### 收藏状态

`GET /api/favorites/{cardId}/status`

需要登录。用于判断当前用户是否已收藏指定卡牌。
