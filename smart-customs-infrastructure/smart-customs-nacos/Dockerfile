# ============================================================================
# 智慧关务平台 - Nacos 服务
# Author: ZHANGCHAO
# ============================================================================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# 步骤 1: 安装系统依赖
# libgcc: 解决 libgcc_s.so.1 缺失报错
# libstdc++ & libc6-compat & gcompat: 解决 Java 本地库兼容性
RUN sed -i "s/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g" /etc/apk/repositories && \
    apk update && \
    apk add --no-cache \
    curl \
    libstdc++ \
    libc6-compat \
    gcompat \
    libgcc \
    tzdata && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone

# 步骤 2: 复制应用文件
COPY ./*.jar /app
COPY ./application.properties /app/application.properties

# 设置 JVM 内存参数 (Nacos 建议至少 512m)
# ENV JAVA_OPTS="-Xmx512m -Xms512m -Dnacos.standalone=true"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dnacos.standalone=true -jar /app/smart-customs-nacos.jar"]
