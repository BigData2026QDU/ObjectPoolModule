# Service Pool

通用服务对象池管理器，基于 Apache Commons Pool2，提供高性能的对象复用能力。

## 简介

Service Pool 是一个轻量级的 Java 对象池框架，用于管理可复用的服务对象。通过池化技术，避免频繁创建和销毁对象带来的性能开销，提升系统并发处理能力。

## 功能特性

- ✅ 基于 Apache Commons Pool2 的高性能对象池
- ✅ 支持按 Class 类型注册、借用、归还、销毁对象
- ✅ 线程安全，支持并发访问
- ✅ 自动预加载，减少首次请求延迟
- ✅ 支持自定义对象创建和销毁逻辑
- ✅ 单例管理器，全局统一管理

## 环境要求

- **JDK：** 17 或更高版本
- **Maven：** 3.6 或更高版本

## 快速开始

### 添加依赖

```xml
<dependency>
    <groupId>com.servicepool</groupId>
    <artifactId>service-pool</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 注册服务

```java
// 在应用启动时注册服务
ServicePoolManager.getInstance().registerService(
    UserService.class, 
    UserService::new, 
    32  // 池大小
);
```

### 使用服务

```java
// 借用服务
UserService userService = ServicePoolManager.getInstance().borrowService(UserService.class);
try {
    // 使用服务
    userService.doSomething();
} finally {
    // 归还服务
    ServicePoolManager.getInstance().returnService(UserService.class, userService);
}
```

## 构建和运行

### 构建项目

```bash
cd service-pool
mvn clean compile
```

### 运行测试

```bash
mvn test
```

### 打包

```bash
mvn clean package
```

## 项目结构

```
service-pool/
├── AGENTS/                          # 规范文档 (submodule)
├── service-pool/                    # 源代码目录
│   ├── src/
│   │   ├── main/java/com/servicepool/
│   │   │   ├── ServicePoolManager.java
│   │   │   └── ServicePooledObjectFactory.java
│   │   └── test/java/com/servicepool/
│   ├── pom.xml
│   ├── README.md
│   └── Architecture.md
├── File_Index.md
└── .gitignore
```

## API 文档

### ServicePoolManager

| 方法 | 说明 |
|------|------|
| `getInstance()` | 获取单例实例 |
| `registerService(Class<T>, Supplier<T>)` | 注册服务（使用默认池大小 32） |
| `registerService(Class<T>, Supplier<T>, int)` | 注册服务（指定池大小） |
| `registerService(Class<T>, Supplier<T>, int, Consumer<T>)` | 注册服务（指定池大小和销毁器） |
| `borrowService(Class<T>)` | 借用服务对象 |
| `returnService(Class<T>, T)` | 归还服务对象 |
| `invalidateService(Class<T>, T)` | 销毁服务对象 |
| `isRegistered(Class<?>)` | 检查服务是否已注册 |
| `shutdown()` | 关闭所有对象池 |

## 开发指南

### 配置说明

默认池大小为 32，可通过 `registerService` 方法的 `poolSize` 参数自定义。

### 最佳实践

1. **池大小配置：** 根据实际业务负载调整，建议 = 平均并发请求数 × 1.5
2. **资源管理：** 始终在 finally 块中归还服务对象
3. **异常处理：** 使用 `invalidateService` 销毁异常对象
4. **生命周期：** 应用关闭时调用 `shutdown()` 释放资源

## 贡献指南

1. Fork 本仓库
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'feat: 添加某个功能'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

## 许可证

MIT License

---

**文档版本：** 1.0  
**更新日期：** 2026-06-26  
**维护者：** xty
