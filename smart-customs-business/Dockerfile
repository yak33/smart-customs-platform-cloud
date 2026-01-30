# ============================================================================
# 智慧关务平台 - 业务服务
# Author: ZHANGCHAO
# ============================================================================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# 步骤 1: 安装系统依赖
# libgcc: 解决 libgcc_s.so.1 缺失报错
# fontconfig & ttf-dejavu: 解决字体依赖
# libstdc++ & libc6-compat & gcompat: 解决 Java 本地库兼容性
RUN sed -i "s/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g" /etc/apk/repositories && \
    apk update && \
    apk add --no-cache \
    ttf-dejavu \
    fontconfig \
    curl \
    libstdc++ \
    libc6-compat \
    gcompat \
    libgcc \
    tzdata && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone

# 步骤 2: 复制应用文件和自定义字体
COPY ./*.jar /app
COPY ./application.yml /app/application.yml

# 步骤 3: 刷新字体缓存
RUN fc-cache -fv

# 设置 JVM 内存参数
# ENV JAVA_OPTS="-Xmx512m -Xms512m"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/smart-customs-business.jar"]
