# 智慧关务平台 - 微服务版 Smart Customs Platform Cloud

<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.4.2-brightgreen.svg" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/Spring%20Cloud-2024.0.0-blue.svg" alt="Spring Cloud"/>
  <img src="https://img.shields.io/badge/Java-21-orange.svg" alt="Java"/>
  <img src="https://img.shields.io/badge/License-Apache%202.0-blue.svg" alt="License"/>
  <img src="https://img.shields.io/badge/Version-0.0.1--SNAPSHOT-yellow.svg" alt="Version"/>
</p>

<p align="center">
  <strong>基于 Spring Cloud Alibaba 构建的企业级微服务架构平台</strong>
</p>

<p align="center">
  <a href="#-项目简介">项目简介</a> •
  <a href="#%EF%B8%8F-技术栈">技术栈</a> •
  <a href="#-项目结构">项目结构</a> •
  <a href="#-快速开始">快速开始</a> •
  <a href="#-开发指南">开发指南</a> •
  <a href="#-更新日志">更新日志</a>
</p>

---

## 📖 项目简介

智慧关务平台微服务版本，是一个基于 **Spring Cloud Alibaba** 和 **Java 21** 构建的现代化企业级分布式应用系统，专注于提供智能化的海关业务管理解决方案。项目由单体版（[smart-customs-platform-server](https://github.com/yak33/smart-customs-platform-server)）改造而来，具备高可用、高扩展性特点，支持多租户、分布式部署，提供完善的权限管理、数据加密、服务治理等企业级特性。

### ✨ 核心特性

- 🚀 **现代化技术栈**：基于 Spring Boot 3.4 + Spring Cloud 2024 + Java 21，支持虚拟线程
- ☁️ **微服务架构**：Spring Cloud Alibaba 服务治理、Nacos 注册配置中心、Gateway 网关
- 🔐 **完善的安全机制**：Sa-Token 认证授权、API加密、数据加密、XSS防护、网关统一鉴权
- 🏢 **多租户架构**：支持 SaaS 模式的多租户数据隔离
- 📊 **动态数据源**：支持主从读写分离、多数据源动态切换
- 🎯 **分布式特性**：Redisson 分布式锁、分布式缓存、分布式事务（Seata）
- 📝 **代码生成器**：支持快速生成 CRUD 代码，提高开发效率
- 📤 **文件存储**：支持本地、MinIO、阿里云 OSS、腾讯云 COS 等多种存储方式
- 📧 **消息通知**：支持邮件、短信等多种通知方式
- 🔄 **实时通信**：支持 SSE、WebSocket 实时推送
- 🌐 **服务间调用**：OpenFeign 声明式调用，支持负载均衡、熔断降级

---

## 🛠️ 技术栈

### 后端框架

| 技术 | 版本 | 说明 |
|-----|------|------|
| Spring Boot | 3.4.2 | 核心框架 |
| Spring Cloud | 2024.0.0 | 微服务框架 |
| Spring Cloud Alibaba | 2023.0.3.4 | 服务治理组件 |
| Nacos | 2.x | 服务注册发现 & 配置中心 |
| Gateway | 4.x | API 网关 |
| OpenFeign | 4.x | 服务间调用 |
| LoadBalancer | 4.x | 负载均衡 |
| MyBatis-Plus | 3.5.14 | ORM 框架 |
| Sa-Token | 1.44.0 | 权限认证框架 |
| Redisson | 3.52.0 | Redis 客户端 & 分布式工具 |
| Lock4j | 2.2.7 | 分布式锁 |
| Dynamic-Datasource | 4.3.1 | 动态数据源 |
| Hutool | 5.8.40 | Java 工具类库 |
| MapStruct Plus | 1.5.0 | 对象映射工具 |
| FastExcel | 1.3.0 | Excel 处理 |
| SMS4J | 3.3.5 | 短信发送 |
| IP2Region | 3.3.1 | IP 地址定位 |
| P6Spy | 3.9.1 | SQL 性能分析 |

### 基础设施

| 组件 | 版本 | 说明 |
|-----|------|------|
| MySQL | 8.0+ | 关系型数据库 |
| Redis | 6.0+ | 缓存数据库 |
| Nacos | 2.x | 服务注册 & 配置中心 |

### 开发工具

- JDK 21
- Maven 3.8+
- Git

---

## 📁 项目结构

```
smart-customs-platform-cloud/
├── pom.xml                           # 父 POM（统一管理版本）
├── smart-customs-dependencies/       # 依赖管理 BOM
│   └── pom.xml                       # 统一依赖版本管理
├── smart-customs-support/            # 支撑代码模块
│   ├── smart-customs-common/         # 公共模块集合
│   │   ├── common-core/              # 核心工具类、通用异常、常量
│   │   ├── common-redis/             # Redis 工具 & 缓存注解
│   │   ├── common-mybatis/           # MyBatis-Plus 配置、分页
│   │   ├── common-satoken/           # Sa-Token 集成、权限工具
│   │   ├── common-web/               # Web 配置、全局异常处理
│   │   ├── common-security/          # 安全模块、数据加密
│   │   ├── common-tenant/            # 多租户支持、数据隔离
│   │   ├── common-excel/             # Excel 导入导出
│   │   ├── common-log/               # 操作日志、登录日志
│   │   ├── common-oss/               # 对象存储（MinIO/阿里/腾讯）
│   │   ├── common-mail/              # 邮件发送
│   │   ├── common-sms/               # 短信发送
│   │   ├── common-idempotent/        # 幂等性处理
│   │   ├── common-ratelimiter/       # 限流器
│   │   ├── common-encrypt/           # 数据库加解密
│   │   ├── common-translation/       # 国际化翻译
│   │   ├── common-sensitive/         # 敏感词过滤
│   │   ├── common-social/            # 社交登录
│   │   ├── common-websocket/         # WebSocket 推送
│   │   ├── common-sse/               # SSE 推送
│   │   └── common-json/              # JSON 序列化
│   └── smart-customs-api/            # 服务间接口定义（Feign）
│       ├── system-api/               # 系统服务 Feign 客户端
│       └── business-api/             # 业务服务 Feign 客户端
├── smart-customs-infrastructure/     # 基础设施服务
│   ├── smart-customs-gateway/        # API 网关（路由、鉴权、限流）
│   └── smart-customs-nacos/          # Nacos 服务（嵌入式）
├── smart-customs-system/             # 系统管理服务
│   └── 用户、角色、部门、菜单、字典、参数、代码生成
├── smart-customs-business/           # 业务核心服务
│   └── 客户企业、海关参数、商品归类、业务数据
└── nacos-config/                     # Nacos 配置文件
    └── *.yaml                        # 各服务共享配置
```

---

## 🌐 服务端口规划

| 服务 | 端口 | 说明 |
|-----|------|------|
| Nacos | 8848 | 服务注册 & 配置中心 |
| Gateway | 9999 | API 网关入口（统一访问入口） |
| System | 9201 | 系统管理服务 + 代码生成器 |
| Business | 9202 | 业务核心服务 |

> **提示**: Nacos 已作为子模块集成，开发环境可通过 Maven 启动

---

## 🚀 快速开始

### 环境要求

- [x] JDK 21+（主项目）/ JDK 8+（Nacos 模块）
- [x] Maven 3.8+
- [x] MySQL 8.0+
- [x] Redis 6.0+

> **注意**: Nacos 模块使用 Spring Boot 2.7.18，需要 JDK 8+

### 配置数据库

1. **创建数据库**

```sql
CREATE DATABASE smart_customs_cloud DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. **导入数据库脚本**

```bash
mysql -u root -p smart_customs_cloud < db/schema.sql
```

### 构建项目

```bash
cd smart-customs-platform-cloud

# 完整构建
mvn clean install -DskipTests

# 仅编译
mvn clean compile -DskipTests
```

### 启动服务

#### 方式一：Maven 启动（开发环境）

按顺序启动以下服务（每个服务一个终端）：

```bash
# 0. 启动 Nacos 服务（嵌入式）
mvn spring-boot:run -pl smart-customs-infrastructure/smart-customs-nacos

# 1. 启动 Gateway 网关
mvn spring-boot:run -pl smart-customs-infrastructure/smart-customs-gateway

# 2. 启动 System 系统服务
mvn spring-boot:run -pl smart-customs-system

# 3. 启动 Business 业务服务
mvn spring-boot:run -pl smart-customs-business
```

#### 方式二：JAR 启动（生产环境）

```bash
# 打包
mvn clean package -DskipTests

# 启动 Nacos
java -jar smart-customs-infrastructure/smart-customs-nacos/target/smart-customs-nacos.jar

# 启动 Gateway
java -jar smart-customs-infrastructure/smart-customs-gateway/target/smart-customs-gateway.jar

# 启动 System 服务
java -jar smart-customs-system/target/smart-customs-system.jar

# 启动 Business 服务
java -jar smart-customs-business/target/smart-customs-business.jar
```

### 访问应用

- **统一入口**：http://localhost:9999（通过 Gateway 访问）
- **Nacos 控制台**：http://localhost:8848/nacos（默认账号密码：nacos/nacos）
- **System 服务**：http://localhost:9201
- **Business 服务**：http://localhost:9202

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
        group: SMART_CUSTOMS_GROUP
```

### 数据库配置

在 Nacos 配置中心添加 `application-common.yml`：

```yaml
spring:
  datasource:
    dynamic:
      primary: master
      datasource:
        master:
          url: jdbc:mysql://localhost:3306/smart_customs_cloud?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
          username: root
          password: your_password
          driver-class-name: com.mysql.cj.jdbc.Driver
```

### Redis 配置

```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
      password: your_password
      database: 0
```

---

## 📦 模块说明

### smart-customs-nacos

Nacos 服务（嵌入式），包含：
- 服务注册与发现
- 配置中心（统一配置管理）
- 内置 Derby/MySQL 数据存储

> 使用 Spring Boot 2.7.18，独立于主项目版本

### smart-customs-gateway

API 网关服务，提供：
- 统一路由转发（System/Business 服务）
- Sa-Token 网关鉴权（JWT Token 验证）
- CORS 跨域配置
- 负载均衡

### smart-customs-system

系统管理服务，包含：
- 用户管理
- 角色权限管理（RBAC）
- 部门组织架构
- 菜单管理
- 字典配置
- 系统参数
- 登录认证
- 代码生成器

### smart-customs-business

业务核心服务，包含：
- 客户企业管理
- 海关参数维护（港口、币制、国别等）
- 商品归类（HS Code）
- 业务数据处理

---

## 📖 开发指南

### 开发环境配置

1. **IDE 推荐**：IntelliJ IDEA 2024+
2. **插件推荐**：
   - Lombok
   - MyBatisX
   - Maven Helper
   - Spring Cloud Alibaba 插件

3. **代码风格**：
   - 遵循阿里巴巴 Java 开发规范
   - 使用 `.editorconfig` 统一代码格式

### 新增业务模块

1. 在 `smart-customs-business` 中创建 Controller/Service/Mapper
2. 在 `business-api` 中定义 Feign 接口（如需跨服务调用）
3. 在 `smart-customs-gateway` 的 `application.yml` 中添加路由规则

### 服务间调用

使用 OpenFeign 进行声明式调用：

```java
// 1. 在 business-api 定义接口
@FeignClient(name = "smart-customs-system", contextId = "systemUser")
public interface SystemFeignClient {

    @GetMapping("/system/user/{userId}")
    R<SysUserDto> getUserById(@PathVariable Long userId);
}

// 2. 在 business 服务注入使用
@Service
@RequiredArgsConstructor
public class SomeService {
    private final SystemFeignClient systemFeignClient;
    
    public void doSomething(Long userId) {
        R<SysUserDto> result = systemFeignClient.getUserById(userId);
        // ...
    }
}
```

---

## 🔐 安全配置

### 密码加密

系统使用 BCrypt 加密算法存储密码，密码强度要求：
- 长度至少 8 位
- 包含大小写字母、数字
- 密码错误次数超过 5 次将锁定账户 10 分钟

### API 接口加密

系统支持 API 接口加密，使用 RSA 非对称加密：

```java
@PostMapping("/encrypt")
@ApiEncrypt  // 开启接口加密
public R<String> encryptData() {
    return R.ok("sensitive data");
}
```

### 数据脱敏

支持敏感字段自动脱敏：

```java
@TableField(typeHandler = EncryptTypeHandler.class)
private String idCard;  // 身份证号自动脱敏
```

---

## 📊 监控和日志

### 应用监控

- 通过 Nacos 控制台查看服务健康状态
- 各服务提供 Spring Boot Actuator 端点

### 日志管理

日志文件位置：`./logs/{service-name}/`
- `info.log`：业务日志
- `warn.log`：警告日志
- `error.log`：错误日志
- `all.log`：全量日志

---

## 🤝 贡献指南

欢迎提交 Issue 和 Pull Request！

### 提交规范

Commit Message 格式：

```
<type>(<scope>): <subject>

<body>

<footer>
```

type 类型：
- ✨ feat：新功能
- 🐛 fix：修复bug
- 📝 docs：文档更新
- 💄 style：代码格式调整
- ♻️ refactor：重构
- ⚡ perf：性能优化
- ✅ test：测试相关
- 🔧 chore：构建/工具链相关

示例：
```
✨ feat(business): 添加客户企业导出功能

增加客户企业数据批量导出为Excel功能
支持自定义导出字段和排序

Closes #123
```

---

## 📝 更新日志

### v0.0.1-SNAPSHOT (2026-01-31)

- ✅ 初始化微服务项目结构
- ✅ 构建 22 个公共模块
- ✅ 创建 Gateway 网关服务
- ✅ 创建 System 系统服务
- ✅ 创建 Business 业务服务
- ✅ 集成 Nacos 服务发现 & 配置中心
- ✅ 支持 OpenFeign 服务间调用
- ♻️ 移除 SpringDoc 接口文档模块

---

## 📄 许可证

[Apache License 2.0](LICENSE)

---

## 👨‍💻 作者

**ZHANGCHAO**

- Email: vuanne1@gmail.com
- GitHub: https://github.com/yak33

---

## 🙏 鸣谢

感谢以下开源项目：

- [Spring Cloud Alibaba](https://sca.aliyun.com/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Nacos](https://nacos.io/)
- [MyBatis-Plus](https://baomidou.com/)
- [Sa-Token](https://sa-token.cc/)
- [Hutool](https://hutool.cn/)
- [RuoYi-Vue-Plus](https://gitee.com/dromara/RuoYi-Vue-Plus)

---

⭐ **如果这个项目对你有帮助，请给一个 Star 支持一下！**

---

**Made with ❤️ by ZHANGCHAO**
