# 文件索引

## 源代码目录

### service-pool/src/main/java/com/servicepool/

| 文件路径 | 作用 | 说明 |
|---------|------|------|
| ServicePoolManager.java | 服务池管理器 | 单例模式，管理多个类型的对象池，提供借出/归还接口 |
| ServicePooledObjectFactory.java | 对象工厂 | 继承 BasePooledObjectFactory，负责创建和销毁池化对象 |

### service-pool/src/test/java/com/servicepool/

| 文件路径 | 作用 | 说明 |
|---------|------|------|
| ServicePoolManagerTest.java | 单元测试 | ServicePoolManager 的测试用例 |
| ServicePooledObjectFactoryTest.java | 单元测试 | ServicePooledObjectFactory 的测试用例 |

## 配置文件

| 文件路径 | 作用 | 说明 |
|---------|------|------|
| service-pool/pom.xml | Maven 配置 | 项目依赖、构建配置 |
| .gitignore | Git 忽略配置 | 忽略 IDE 文件、构建产物等 |

## 文档文件

| 文件路径 | 作用 | 说明 |
|---------|------|------|
| service-pool/README.md | 项目说明 | 使用指南、API 文档 |
| service-pool/Architecture.md | 架构文档 | 系统设计、模块职责、数据流向 |
| File_Index.md | 文件索引 | 本文件 |

## 目录结构

```
service-pool/
├── AGENTS/                          # 规范文档 (submodule)
│   ├── AGENTS.md
│   ├── CLAUDE.md
│   └── PROJECT/
├── service-pool/                    # 源代码目录（与仓库同名）
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

---

**文档版本：** 1.0  
**更新日期：** 2026-06-26  
**维护者：** xty
