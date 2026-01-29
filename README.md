# 智慧关务平台 - 微服务版

<p align="center">
  <strong>SmartCustomsPlatform Cloud</strong>
</p>

<p align="center">
  基于 Spring Cloud 2024 + Spring Cloud Alibaba 构建的企业级微服务架构平台
</p>

---

## 📋 项目概述

智慧关务平台微服务版本，采用 Spring Cloud Alibaba 技术栈实现服务治理，具备高可用、高扩展性特点。

### 技术栈

| 组件 | 版本 | 说明 |
|-----|------|------|
| Spring Boot | 3.4.2 | 基础框架 |
| Spring Cloud | 2024.0.0 | 微服务框架 |
| Spring Cloud Alibaba | 2023.0.3.4 | 服务治理 |
| Nacos | 2.x | 服务注册 & 配置中心 |
| Sa-Token | 1.44.0 | 认证授权 |
| MyBatis-Plus | 3.5.14 | ORM 框架 |
| Redisson | 3.52.0 | 分布式锁 & 缓存 |
| JDK | 21 | 运行环境 |

---

## 📁 项目结构

```
smart-customs-platform-cloud/
├── pom.xml                           # 父 POM
├── smart-customs-dependencies/       # 依赖管理 BOM
├── smart-customs-common/             # 公共模块 (22 个子模块)
│   ├── common-core/                  # 核心工具类
│   ├── common-redis/                 # Redis 工具
│   ├── common-mybatis/               # MyBatis-Plus 配置
│   ├── common-satoken/               # Sa-Token 集成
│   ├── common-web/                   # Web 相关
│   ├── common-security/              # 安全模块
│   ├── common-tenant/                # 多租户支持
│   └── ...                           # 其他公共模块
├── smart-customs-api/                # 服务间接口定义
│   ├── system-api/                   # 系统服务 Feign 客户端
│   └── business-api/                 # 业务服务 Feign 客户端
├── smart-customs-gateway/            # API 网关服务
├── smart-customs-system/             # 系统管理服务
├── smart-customs-business/           # 业务核心服务
└── smart-customs-nacos/              # Nacos 服务 (嵌入式)
```

---

## 🌐 服务端口规划

| 服务 | 端口 | 说明 |
|-----|------|------|
| Nacos | 8848 | 服务注册 & 配置中心 |
| Gateway | 9999 | API 网关入口 |
| System | 9201 | 系统管理 + 代码生成 |
| Business | 9202 | 业务核心服务 |

> **提示**: Nacos 已作为子模块集成，可通过 Maven 启动

---

## 🚀 快速开始

### 1. 环境准备

- [x] JDK 21+ (主项目) / JDK 8+ (Nacos 模块)
- [x] Maven 3.8+
- [x] MySQL 8.0+
- [x] Redis 6.0+

> **注意**: Nacos 模块使用 Spring Boot 2.7.18，需要 JDK 8+

### 2. 配置 Nacos

启动 Nacos 服务，确保以下地址可访问：

```
http://<nacos-host>:8848/nacos
```

### 3. 构建项目

```bash
cd smart-customs-platform-cloud

# 完整构建
mvn clean install -DskipTests

# 仅编译
mvn clean compile -DskipTests
```

### 4. 启动服务

#### 方式一：Maven 启动（开发环境）

```bash
# 0. 启动 Nacos 服务（嵌入式，子模块方式）
mvn spring-boot:run -pl smart-customs-nacos

# 1. 启动 Gateway（新终端）
mvn spring-boot:run -pl smart-customs-gateway

# 2. 启动 System 服务（新终端）
mvn spring-boot:run -pl smart-customs-system

# 3. 启动 Business 服务（新终端）
mvn spring-boot:run -pl smart-customs-business
```

#### 方式二：JAR 启动（生产环境）

```bash
# 打包
mvn clean package -DskipTests

# 启动
java -jar smart-customs-nacos/target/smart-customs-nacos.jar
java -jar smart-customs-gateway/target/smart-customs-gateway.jar
java -jar smart-customs-system/target/smart-customs-system.jar
java -jar smart-customs-business/target/smart-customs-business.jar
```

### 5. 验证服务

- 访问 Nacos 控制台，确认服务已注册
- 通过 Gateway 访问：`http://localhost:9999`

---

## ⚙️ 配置说明

### Nacos 配置

各服务通过 `bootstrap.yml` 连接 Nacos：

```yaml
spring:
  cloud:
    nacos:
      discovery:
        server-addr: ${NACOS_SERVER:localhost:8848}
        namespace: ${NACOS_NAMESPACE:public}
        group: SMART_CUSTOMS_GROUP
      config:
        server-addr: ${NACOS_SERVER:localhost:8848}
        file-extension: yml
```

### 数据库配置

在 Nacos 配置中心或本地 `application.yml` 中配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/smart_customs?useUnicode=true&characterEncoding=utf8
    username: root
    password: your_password
```

### Redis 配置

```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
      password: your_password
```

---

## 📦 模块说明

### smart-customs-nacos

Nacos 服务（嵌入式），包含：
- 服务注册与发现
- 配置中心
- 内置 Derby/MySQL 数据存储

> 使用 Spring Boot 2.7.18，独立于主项目版本

### smart-customs-gateway

API 网关服务，提供：
- 统一路由转发
- Sa-Token 网关鉴权
- CORS 跨域配置
- 流量控制

### smart-customs-system

系统管理服务，包含：
- 用户管理
- 角色权限
- 部门组织
- 菜单管理
- 字典配置
- 登录认证
- 代码生成

### smart-customs-business

业务核心服务，包含：
- 客户企业管理
- 海关参数维护
- 商品归类
- 业务数据处理

---

## 📖 开发指南

### 新增业务模块

1. 在 `smart-customs-business` 中创建 Controller/Service/Mapper
2. 在 `business-api` 中定义 Feign 接口（如需跨服务调用）
3. 在 `smart-customs-gateway` 中添加路由规则

### 服务间调用

使用 OpenFeign 进行声明式调用：

```java
@FeignClient(name = "smart-customs-system", contextId = "systemUser")
public interface SystemFeignClient {

    @GetMapping("/system/user/{userId}")
    R<SysUserDto> getUserById(@PathVariable Long userId);
}
```

---

## 📝 更新日志

### v0.0.1-SNAPSHOT (2026-01-28)

- ✅ 初始化微服务项目结构
- ✅ 构建 22 个公共模块
- ✅ 创建 Gateway 网关服务
- ✅ 创建 System 系统服务
- ✅ 创建 Business 业务服务
- ✅ 集成 Nacos 服务发现

---

## 👨‍💻 作者

**ZHANGCHAO**

---

## 📄 许可证

[Apache License 2.0](LICENSE)
