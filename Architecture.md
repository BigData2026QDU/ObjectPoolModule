# 系统架构

## 1. 整体架构

Service Pool 是一个通用的服务对象池管理器，基于 Apache Commons Pool2 构建，提供高性能的对象复用能力。

```
┌─────────────────────────────────────────────────────────────┐
│                    ServicePoolManager                       │
│                    (单例管理器)                              │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐        │
│  │  UserService │  │ OrderService│  │ CacheService│  ...   │
│  │    Pool      │  │    Pool     │  │    Pool     │        │
│  └─────────────┘  └─────────────┘  └─────────────┘        │
│         │                │                │                 │
│         └────────────────┼────────────────┘                 │
│                          │                                  │
│                          ▼                                  │
│              ┌───────────────────────┐                     │
│              │   GenericObjectPool   │                     │
│              │   (Apache Commons)    │                     │
│              └───────────────────────┘                     │
└─────────────────────────────────────────────────────────────┘
```

## 2. 核心模块

### 2.1 ServicePoolManager（服务池管理器）

**职责：**
- 管理多个类型的对象池
- 提供统一的借出/归还接口
- 线程安全的并发访问支持
- 生命周期管理（初始化、关闭）

**关键特性：**
- 单例模式，全局唯一实例
- 使用 ConcurrentHashMap 存储多个对象池
- 支持自定义对象创建和销毁逻辑

### 2.2 ServicePooledObjectFactory（对象工厂）

**职责：**
- 创建新的池化对象
- 包装对象为 PooledObject
- 销毁不再使用的对象

**关键特性：**
- 支持自定义创建者（Supplier）
- 支持自定义销毁者（Consumer）
- 自动处理 AutoCloseable 对象

## 3. 数据流向

```
应用代码
    │
    ▼
borrowService(Class<T>)
    │
    ▼
ServicePoolManager.getPool()
    │
    ▼
GenericObjectPool.borrowObject()
    │
    ├── 池中有空闲对象 → 返回对象
    │
    └── 池中无空闲对象 → 调用 Factory.create() 创建新对象
    │
    ▼
使用对象
    │
    ▼
returnService(Class<T>, T)
    │
    ▼
GenericObjectPool.returnObject()
    │
    ▼
对象回到池中，等待下次复用
```

## 4. 技术选型

| 技术 | 版本 | 用途 | 选择理由 |
|------|------|------|----------|
| Java | 17 | 开发语言 | LTS 版本，稳定可靠 |
| Apache Commons Pool2 | 2.12.0 | 对象池实现 | 成熟稳定的对象池框架 |
| Maven | - | 构建工具 | 业界标准，依赖管理便捷 |

## 5. 性能考虑

### 5.1 池大小配置

- **默认池大小：** 32
- **建议：** 根据实际业务负载调整
- **公式：** 池大小 ≈ 平均并发请求数 × 1.5

### 5.2 线程安全

- ServicePoolManager 使用 ConcurrentHashMap
- GenericObjectPool 内部已处理线程同步
- 借出/归还操作是线程安全的

### 5.3 内存占用

- 每个对象池元数据：约 1KB
- 每个池化对象：约 100-500 字节（取决于对象本身）
- 32 个对象的池：约 3-16KB

## 6. 扩展性

### 6.1 自定义对象生命周期

```java
// 注册时提供自定义销毁器
ServicePoolManager.getInstance().registerService(
    DatabaseConnection.class,
    DatabaseConnection::new,
    10,
    conn -> {
        // 自定义关闭逻辑
        conn.closeQuietly();
    }
);
```

### 6.2 多类型支持

```java
// 注册不同类型的服务
ServicePoolManager.getInstance().registerService(UserService.class, UserService::new);
ServicePoolManager.getInstance().registerService(OrderService.class, OrderService::new);
ServicePoolManager.getInstance().registerService(CacheService.class, CacheService::new);
```

---

**文档版本：** 1.0  
**更新日期：** 2026-06-26  
**维护者：** xty
